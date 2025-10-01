package com.nhnacademy.shoppingmall.common.mvc.controller;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.exception.ControllerNotFoundException;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ControllerFactory {
    public static final String CONTEXT_CONTROLLER_FACTORY_NAME = "CONTEXT_CONTROLLER_FACTORY";
    private final ConcurrentMap<String, Object> beanMap = new ConcurrentHashMap<>();

    public void initialize(Set<Class<?>> c, ServletContext ctx){

        if(Objects.isNull(c)){
            log.info("Controller not found");
            return;
        }

        /* todo#5-1 ControllerFactory 초기화, 아래 설명을 참고하여 구현합니다.
         * 1. Set<Class<?>> c 에는 com.nhnacademy.shoppingmall.common.initialize.WebAppInitializer 에서 HandlesTypes에
         *  com.nhnacademy.shoppingmall.common.mvc.controller.BaseController.class인 class를 set에 담겨서 parameter로 전달 됩니다.
         *  BaseController를 구현한 Controller class가 전달됩니다.
         *
         * 2.Java Reflection API를 사용하여 Controller class의 instance를 생성하고 beanMap에 등록합니다. key/value는 다음과 같습니다.
         *  ex) key= GET-/index.do , value = IndexController's instance
         *
         * 3. @RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do","/main.do"}) 처럼 value는 String 배열일 수 있습니다.
         *  즉 /index.do, /main.do -> IndexController로 맵핑 됩니다.
         */
        log.debug("ControllerFactory 초기화 시작: 총 {}개의 컨트롤러 클래스 발견", c.size());

        // 2. 수집된 컨트롤러 클래스들을 반복 처리
        for(Class<?> controllerClass: c){
            try{
                // @RequestMapping 어노테이션 확인
                if(!controllerClass.isAnnotationPresent(RequestMapping.class)){
                    log.warn("@RequestMapping 어노테이션 없는 컨트롤러: {}", controllerClass.getName());
                    continue;
                }
                // 어노테이션에서 메타데이터 추출
                RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
                RequestMapping.Method method = requestMapping.method();
                String[] paths = requestMapping.value();

                // 컨트롤러 인스턴스 생성
                BaseController controllerInstance = (BaseController) controllerClass
                        .getDeclaredConstructor()
                        .newInstance();

                // 각 경로에 대해 매핑 등록
                for(String path : paths){
                    String key = getKey(method.name(), path);
                    beanMap.put(key, controllerInstance);
                    log.debug("Controller Mapped: {} - {} -> {}",
                            method.name(), path, controllerClass.getSimpleName());
                }

            } catch (Exception e) {
                // 인스턴스 생성 또는 타입 변환 실패 시 예외 처리
                log.error("컨트롤러({}) 초기화 중 오류 발생: {}", controllerClass.getName(), e.getMessage(), e);
                throw new RuntimeException("Controller 초기화 실패: " + controllerClass.getName(), e);
            }

            // todo#5-2 ctx(ServletContext)에 attribute를 추가합니다. -> key : CONTEXT_CONTROLLER_FACTORY_NAME, value : ControllerFactory
            ctx.setAttribute(CONTEXT_CONTROLLER_FACTORY_NAME, this);
            log.info("ControllerFactory가 ServletContext에 키 '{}'로 저장되었습니다. 총 {}개의 매핑 등록",
                    CONTEXT_CONTROLLER_FACTORY_NAME, beanMap.size());

        }
    }

    private Object getBean(String key){
        //todo#5-3 beanMap에서 controller 객체를 반환 합니다.
        Object controller = beanMap.get(key);
        if(Objects.isNull(controller)){ // key:"GET-/index.jsp 즉 key가 없으면 controller는 null
            throw new ControllerNotFoundException("Controller no found for key: " + key);
        }
        return controller; // beanMap.get(GET-/login.do) -> LoginController 인스턴스 반환
    }

    public Object getController(HttpServletRequest request){ // 1.request
        //todo#5-4 request의 method, servletPath를 이용해서 Controller 객체를 반환합니다.
        String method = request.getMethod(); // "GET"
        String servletPath = request.getServletPath(); // "/login.do"

        return getController(method,servletPath);
    }

    public Object getController(String method, String path){ // 2.
        //todo#5-5 method, path를 이용해서 Controller 객체를 반환 합니다.
        String key = getKey(method,path);
        return getBean(key); // key = GET-/login.do
    }

    private String getKey(String method, String path){
        //todo#5-6  {method}-{key}  형식으로 Key를 반환 합니다.
        //ex GET-/index.do
        //ex POST-/loginAction.do

        return method.toUpperCase() + "-" + path;
    }
}
