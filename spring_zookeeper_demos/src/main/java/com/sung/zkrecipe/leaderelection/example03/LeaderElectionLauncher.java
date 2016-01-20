package com.sung.zkrecipe.leaderelection.example03;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by sungang on 2016/1/18.14:52
 * This is a launcher class responsible for starting the thread of ProcessNode implementation.
 * Since Apache ZooKeeper client runs daemon processes for notifying about watchevent,
 * this class uses ExecutorService to start ProcessNode so that program doesn't exit after ProcessNode main thread is finished executing.
 */
public class LeaderElectionLauncher {

    private static final Logger LOG = Logger.getLogger(LeaderElectionLauncher.class);

    public static void main(String[] args) throws IOException {

//        if (args.length < 2) {
//            System.err.println("Usage: java -jar <jar_file_name> <process id integer> <zkhost:port pairs>");
//            System.exit(2);
//        }

        final int id = 2;
        final String zkURL = "10.143.132.232:2181";

        final ExecutorService service = Executors.newSingleThreadExecutor();

        final Future<?> status = service.submit(new ProcessNode(id, zkURL));

        try {
            status.get();
        } catch (InterruptedException | ExecutionException e) {
            LOG.fatal(e.getMessage(), e);
            service.shutdown();
        }
    }
}
