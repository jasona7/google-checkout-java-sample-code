package com.google.checkout.orderprocessing.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.MerchantConstants;
import com.google.checkout.impl.util.Constants;
import com.google.checkout.impl.util.Utils;
import com.google.checkout.orderprocessing.AbstractAddMerchantOrderNumberRequest;

/**
 * @author 		ksim
 * @date   		March 6th, 2007
 * @version		1.0 - ksim - March 6th, 2007 - 	Initial Version
 * 				1.1 - ksim - March 10th, 2007 - Fixed and made changes to reference correct Util functions.
 *
 */

public final class AddMerchantOrderNumberRequestImpl extends AbstractAddMerchantOrderNumberRequest {

	Document document;
	Element root;
	
	public AddMerchantOrderNumberRequestImpl(MerchantConstants merchantConstants, String googleOrderNo, String merchantOrderNo) {
		super(merchantConstants, googleOrderNo, merchantOrderNo);

		
	      document = Utils.newEmptyDocument();
	      root =  (Element) document.createElementNS(Constants.checkoutNamespace, "add-merchant-order-number"); 
	      root.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns", Constants.checkoutNamespace);
	      root.setAttribute("google-order-number", googleOrderNo);
	      document.appendChild(root);
	      	
	      Utils.createNewElementAndSet(document, root, "merchant-order-number", merchantOrderNo);
	}
	
	public String getXml() {
		return Utils.documentToString(document);
	}
	
	public String getXmlPretty() {
		return Utils.documentToString(document);

	}
}
