/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.google.checkout.orderprocessing.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.checkout.MerchantConstants;
import com.google.checkout.impl.AbstractCheckoutRequest;
import com.google.checkout.impl.util.Constants;
import com.google.checkout.impl.util.Utils;
import com.google.checkout.orderprocessing.SendBuyerMessageRequest;

/**
 * Default implementation of the SendBuyerMessageRequest interface.
 * 
 * @author ksim
 * @version 1.0 - ksim - March 10th, 2007 - Initial Version
 */

public class SendBuyerMessageRequestImpl extends AbstractCheckoutRequest
    implements SendBuyerMessageRequest {

  private Document document;

  private Element root;

  /**
   * Constructor which takes an instance of MerchantConstants.
   * 
   * @param merchantConstants
   *          The MerchantConstants.
   * 
   * @see MerchantConstants
   */
  public SendBuyerMessageRequestImpl(MerchantConstants merchantConstants) {
    super(merchantConstants);
    document = Utils.newEmptyDocument();
    root = (Element) document.createElementNS(Constants.checkoutNamespace,
        "send-buyer-message");
    root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
        Constants.checkoutNamespace);
    document.appendChild(root);
  }

  /**
   * Constructor which takes an instance of MerchantConstants, Google Order
   * Number and Message.
   * 
   * @param merchantConstants
   *          The MerchantConstants.
   * @param googleOrderNo
   *          The Google Order Number.
   * @param message
   *          The Message.
   * 
   * @see MerchantConstants
   */
  public SendBuyerMessageRequestImpl(MerchantConstants merchantConstants,
      String googleOrderNo, String message) {
    this(merchantConstants);
    this.setGoogleOrderNo(googleOrderNo);
    this.setMessage(message);
  }

  /**
   * Constructor which takes an instance of MerchantConstants, Google Order
   * Number, Message and Send Email flag.
   * 
   * @param merchantConstants
   *          The MerchantConstants.
   * @param googleOrderNo
   *          The Google Order Number.
   * @param message
   *          The Message.
   * @param sendEmail
   *          The Send Email flag.
   * 
   * @see MerchantConstants
   */
  public SendBuyerMessageRequestImpl(MerchantConstants merchantConstants,
      String googleOrderNo, String message, boolean sendEmail) {
    this(merchantConstants, googleOrderNo, message);
    this.setGoogleOrderNo(googleOrderNo);
    this.setMessage(message);
    this.setSendEmail(sendEmail);
  }

  /**
   * Determine whether the message is within the string length limits.
   * 
   * @param message
   *          The Message.
   * @return True or false.
   */
  public boolean isWithinMessageStringLimits(String message) {
    int lenStrMessage = message.length();

    if (lenStrMessage <= Constants.messageStrLimit)
      return true;
    else
      return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getXml()
   */
  public String getXml() {
    return Utils.documentToString(document);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getXmlPretty()
   */
  public String getXmlPretty() {
    return Utils.documentToStringPretty(document);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.CheckoutRequest#getPostUrl()
   */
  public String getPostUrl() {
    return merchantConstants.getRequestUrl();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.orderprocessing.SendBuyerMessageRequest#getGoogleOrderNo()
   */
  public String getGoogleOrderNo() {
    return root.getAttribute("google-order-number");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.orderprocessing.SendBuyerMessageRequest#getMessage()
   */
  public String getMessage() {
    return Utils.getElementStringValue(document, root, "message");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.orderprocessing.SendBuyerMessageRequest#isSendEmail()
   */
  public boolean isSendEmail() {
    return Utils.getElementBooleanValue(document, root, "send-email");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.orderprocessing.SendBuyerMessageRequest#setGoogleOrderNo(java.lang.String)
   */
  public void setGoogleOrderNo(String googleOrderNo) {
    root.setAttribute("google-order-number", googleOrderNo);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.orderprocessing.SendBuyerMessageRequest#setMessage(java.lang.String)
   */
  public void setMessage(String message) {
    if (!isWithinMessageStringLimits(message)) {
      message = "";
      System.err.println(Constants.messageErrorString);
    }

    Utils.findElementAndSetElseCreateAndSet(document, root, "message", message);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.checkout.orderprocessing.SendBuyerMessageRequest#setSendEmail(boolean)
   */
  public void setSendEmail(boolean sendEmail) {
    Utils.findElementAndSetElseCreateAndSet(document, root, "send-email",
        sendEmail);
  }
}
