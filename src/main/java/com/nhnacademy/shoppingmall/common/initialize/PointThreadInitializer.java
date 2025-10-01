package com.nhnacademy.shoppingmall.common.initialize;

import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.worker.WorkerThread;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.util.Set;

@Slf4j
public class PointThreadInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

        RequestChannel requestChannel = new RequestChannel(10);

        //todo#14-1 servletContext에 requestChannel을 등록합니다.

        ctx.setAttribute("requestChannel", requestChannel);
        log.info("RequestChannel registered in servlet context");

        //todo#14-2 WorkerThread 사작합니다.

        int workerCount = 5;

        for (int i = 0; i < workerCount; i++) {
            WorkerThread worker = new WorkerThread(requestChannel);
            worker.setName("WorkerThread-" + (i + 1));
            worker.start();
            log.info("{} started.",worker.getName());

        }

    }
}
