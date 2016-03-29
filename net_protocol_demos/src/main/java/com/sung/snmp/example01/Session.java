package com.sung.snmp.example01;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;


public class Session {
	private Writer writer;
	private CommunityTarget  target;
	private ResponseListener listener;
	private Map<Integer32,String> walkList;
	// Needed because bulk responses come one at a time
	private Integer32 lastResponseId;
	private long lastResponseTime;
	
	private int requests;
	private int responses;
	
	public Session(String address, Writer w) 
	{
		writer = w;
		requests = 0;
		responses = 0;
		walkList = new HashMap<Integer32,String>();

		lastResponseTime = 0;

		Address targetAddress = GenericAddress.parse(address);
	    // setting up target
		target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		//target.setVersion(SnmpConstants.version1);
		target.setVersion(SnmpConstants.version2c);

		// set up a listener to use. 
		System.out.println("create listener");
		listener = new ResponseListener() {
			  public void onResponse(ResponseEvent event) {
			    // Always cancel async request when response has been received
			    // otherwise a memory leak is created! Not canceling a request
			    // immediately can be useful when sending a request to a broadcast
			    // address.
			    ((Snmp)event.getSource()).cancel(event.getRequest(), this);
			    ++responses;

			    PDU pdu = event.getResponse();
			    writer.writePDU(event.getPeerAddress(), pdu, getResponseTime(pdu));
			    // check if it's a walk
			    Integer32 oldReqId = pdu.getRequestID();
			    String oid = walkList.get(oldReqId);
			    if (oid != null)
			    {
			    	// it's a walk
			    	walkList.remove(oldReqId);
			    	// get the last one, in case it's bulk
			    	String retOid = pdu.getVariableBindings().get(pdu.getVariableBindings().size()-1).getOid().toString();
			    			
			    	if (retOid.startsWith(oid) && !retOid.equals(oid))
			    	{
			    		Integer32 reqId = walkCore(retOid);
			    		//--do some bookkeeping
			    		walkList.put(reqId, oid); // keep the original OID
			    	}
			    }
			 }
		};	
	}
	
	private long getResponseTime(PDU pdu)
	{
		if (lastResponseId != null)
		{
			if (lastResponseId.equals(pdu.getRequestID()))
			{
				return lastResponseTime;
			}
		}
		lastResponseTime = Writer.getElapsedTime();
		lastResponseId = pdu.getRequestID();
		return lastResponseTime;
	}
	
	public void getBulk(String oid)
	{
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid)));
		pdu.setType(PDU.GETBULK);
		pdu.setMaxRepetitions(16);
		
		int reqId = Snmp4jTest.snmp.getNextRequestID();
		pdu.setRequestID(new Integer32(reqId));
		send(pdu);
		//--register since this is a walk
	}
	
	public void get(List<String> oids)
	{
		PDU pdu = new PDU();
		for (String oid: oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		pdu.setType(PDU.GET);
		
		int reqId = Snmp4jTest.snmp.getNextRequestID();
		pdu.setRequestID(new Integer32(reqId));
		send(pdu);
	}
	
	public void getNext(List<String> oids)
	{
		PDU pdu = new PDU();
		for (String oid: oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		int reqId = Snmp4jTest.snmp.getNextRequestID();
		pdu.setRequestID(new Integer32(reqId));
		pdu.setType(PDU.GETNEXT);
		send(pdu);
	}
	
	public void walk(String oid)
	{
		Integer32 reqId = walkCore(oid);
		walkList.put(reqId, oid);
	}
	
	private Integer32 walkCore(String oid)
	{
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid)));
		pdu.setType(PDU.GETNEXT);
		Integer32 reqId = new Integer32(Snmp4jTest.snmp.getNextRequestID());
		pdu.setRequestID(reqId);
		send(pdu);
		return reqId;
	}
		
	
	private void send(PDU pdu)
	{
		try {
			writer.writePDU(target.getAddress(), pdu);
			Snmp4jTest.snmp.send(pdu, target, null, listener);
			++requests;
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public boolean isComplete()
	{
		return ((responses >= requests) && walkList.isEmpty());
	}
	
	public void printStats()
	{
		if (isComplete())
			System.out.println(target.getAddress()+": Completed. "+requests+" request, "+responses+" responses");
		else
			System.out.println(target.getAddress()+": In Progress. "+requests+" request, "+responses+" responses");

	}
}
