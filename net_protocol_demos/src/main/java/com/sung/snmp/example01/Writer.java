package com.sung.snmp.example01;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.snmp4j.PDU;
import org.snmp4j.smi.Address;


public class Writer {

	private BufferedWriter out;

	private static long startTime;
	
	public Writer(String filename) throws IOException
	{
		startTime = System.currentTimeMillis();

		FileWriter fstream = new FileWriter(filename);
		out = new BufferedWriter(fstream);
		writeLine(getHeader());
	}
	
	public static long getElapsedTime() {
		return System.currentTimeMillis()-startTime;
	}
	
	public void close() {
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(String text) {
		try {
			out.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeLine(String text) {
		write(text+'\n');
	}
	
	public void writePDU(Address session, PDU pdu)
	{
		writeLine(pduToString(session,pdu, getElapsedTime()));
	}
	
	public void writePDU(Address session, PDU pdu, long elapsedTime)
	{
		writeLine(pduToString(session,pdu, elapsedTime));
	}
	
	public String getHeader() {
		return "\nTime\tSession\tType\tReqestId\tStatus\tSyntax\tOID\tValue";
	}
	
	public static String pduToString(Address session, PDU pdu, long elapsedTime) {
	    StringBuffer buf = new StringBuffer();
	    boolean isRequest = ((pdu.getType() != PDU.GET) && (pdu.getType() != PDU.GETBULK) && (pdu.getType() != PDU.GETNEXT));
	    for (int i=0; i<pdu.getVariableBindings().size(); i++) {
	    	if (i != 0)
	    		buf.append("\n");
	    	buf.append(elapsedTime/1000F);  //time
	    	// remove the /161 port from the session
	    	int idx = session.toString().indexOf("/");
	    	if (idx == -1) {
	    		buf.append("\t"+session.toString()); // session
	    	} else {
	    		buf.append("\t"+session.toString().substring(0, idx)); // session		
	    	}
	    	buf.append("\t"+PDU.getTypeString(pdu.getType())); // command
	    	buf.append("\t"+pdu.getRequestID()); // regId
	    	buf.append("\t"+pdu.getErrorStatus()+"("+pdu.getErrorStatusText()+")"); // status
	    	buf.append("\t"); // syntax
	    	if (isRequest) {
	    		buf.append(pdu.getVariableBindings().get(i).getVariable().getSyntaxString()); 
	    	}
	    	buf.append("\t"+pdu.getVariableBindings().get(i).getOid()); // OID
	    	buf.append("\t"); // value
	    	if (isRequest) {
	    		buf.append(pdu.getVariableBindings().get(i).toValueString());
	    	}
	    }
	    return buf.toString();
	}
	
}
