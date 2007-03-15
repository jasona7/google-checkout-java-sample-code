package com.google.checkout.orderprocessing.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.MerchantConstants;
import com.google.checkout.impl.AbstractCheckoutRequest;
import com.google.checkout.impl.util.Constants;
import com.google.checkout.impl.util.Utils;
import com.google.checkout.orderprocessing.RefundOrderRequest;

/**
 * @author 		ksim
 * @date   		March 10th, 2007
 * @version		1.0 - ksim - March 10th, 2007 - Initial Version
 *
 */

public final class RefundOrderRequestImpl extends AbstractCheckoutRequest implements RefundOrderRequest {
	Document document;
	Element root;
	
	public RefundOrderRequestImpl(MerchantConstants merchantConstants, String googleOrderNo, String reason) {
		super(merchantConstants);
		
		if (!isWithinRefundStringLimits(reason, ""))
		{
			reason = "";
			System.err.println(Constants.refundErrorString);
		}

	      document = Utils.newEmptyDocument();
	      root =  (Element) document.createElementNS(Constants.checkoutNamespace, "refund-order"); 
	      root.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns", Constants.checkoutNamespace);
	      root.setAttribute("google-order-number", googleOrderNo);
	      document.appendChild(root);
	      
	      Element reasonTag;
	      reasonTag =  (Element) document.createElement("reason");
	      root.appendChild(reasonTag);
	      reasonTag.appendChild(document.createTextNode(reason));
	}
	
	public RefundOrderRequestImpl(MerchantConstants merchantConstants, String googleOrderNo, String reason, float amount, String comment) {
		this(merchantConstants, googleOrderNo, reason);
		
		if (!isWithinRefundStringLimits("", comment))
		{
			comment = "";
			System.err.println(Constants.refundErrorString);
		}
		
	    Utils.createNewElementAndSetAndAttribute(document, root, "amount", new Float(amount).toString(), "currency", merchantConstants.getCurrencyCode());
	    Utils.createNewElementAndSet(document, root, "comment", comment);
	}
	
	public boolean isWithinRefundStringLimits(String reason, String comment)
	{
		int lenStrReason = reason.length();
		int lenStrComment = comment.length();
		
		if (lenStrReason <= Constants.refundStrLimit && lenStrComment <= Constants.refundStrLimit)
			return true;
		else
			return false;
	}
	
	public void addAmount(float amt)
	{
		Utils.findElementElseCreateAndSetAndAttribute(document, root, "amount", new Float(amt).toString(), "currency", merchantConstants.getCurrencyCode());	
	}
	
	public void addComment(String cmt)
	{
		if (!isWithinRefundStringLimits("", cmt))
		{
			System.err.println(Constants.refundErrorString);
			return;
		}
		
		Utils.findElementElseCreateAndSet(document, root, "comment", cmt);
	}
	
	public String getXml() {
		return Utils.documentToString(document);
	}
	
	public String getXmlPretty() {
		return Utils.documentToString(document);

	}

	public String getPostUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public float getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getComment() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getGoogleOrderNo() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getReason() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAmount(float amount) {
		// TODO Auto-generated method stub
		
	}

	public void setComment(String comment) {
		// TODO Auto-generated method stub
		
	}

	public void setGoogleOrderNo(String googleOrderNo) {
		// TODO Auto-generated method stub
		
	}

	public void setReason(String reason) {
		// TODO Auto-generated method stub
		
	}
}