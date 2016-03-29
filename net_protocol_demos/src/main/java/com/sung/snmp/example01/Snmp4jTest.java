package com.sung.snmp.example01;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultUdpTransportMapping;
/**
 * Created by sungang on 2016/3/9.16:10
 */
public class Snmp4jTest {
    public static Snmp snmp;
    public Writer writer;

    public Snmp4jTest(String outfile) {

        try {
            writer = new Writer(outfile);
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        TransportMapping transport;
        try {
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()), 0);
            SecurityModels.getInstance().addSecurityModel(usm);
            transport.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void get()
    {
        // Replace IP1 to IP4 with real IP addresses.
        Session session = new Session("udp:"+""+"/161", writer);

        // creating PDU
        session.getBulk("1.3.6.1.2.1.1.1");
        session.getBulk("1.3.6.1.2.1.1.2");

        List<String> oList = new ArrayList<String>();
        oList.add("1.3.6.1.2.1.25.3.2.1.3.768");
        oList.add("1.3.6.1.2.1.25.3.2.1.3.770");

        session.get(oList);


        // creating PDU
        session.walk("1.3.6.1.2.1.25.3.2.1.3");

        // wait for all sessions to complete
        while (!session.isComplete())
        {
            session.printStats();
            try {
                Thread.currentThread();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }//sleep for 100 ms

        }
        session.printStats();
        writer.close();
    }

    public static void main(String args[]){
        if (args.length >= 1) {
            System.out.println("Start");
            Snmp4jTest getter = new Snmp4jTest("d://11.txt");
            getter.get();
            System.out.println("End");
        } else {
            System.out.println("Arguments missing.\nUsage: GetSNMP <outfile>");
        }
    }
}
