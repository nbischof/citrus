/*
 * Copyright 2006-2009 ConSol* Software GmbH.
 * 
 * This file is part of Citrus.
 * 
 *  Citrus is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Citrus is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Citrus.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.consol.citrus.config.xml;

import java.util.*;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class ReceiveMessageActionParser implements BeanDefinitionParser {

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String parent = element.getAttribute("parent");
        String messageReceiverReference = element.getAttribute("with");
        
        BeanDefinitionBuilder builder;

        if (StringUtils.hasText(parent)) {
            builder = BeanDefinitionBuilder.childBeanDefinition(parent);
            builder.addPropertyValue("name", element.getLocalName() + ":" + parent);
        } else if (StringUtils.hasText(messageReceiverReference)) {
            builder = BeanDefinitionBuilder.genericBeanDefinition("com.consol.citrus.actions.ReceiveMessageAction");
            builder.addPropertyValue("name", element.getLocalName());
            
            builder.addPropertyReference("messageReceiver", messageReceiverReference);
        } else {
            throw new BeanCreationException("Either 'parent' or 'with' attribute has to be set!");
        }
        
        DescriptionElementParser.doParse(element, builder);

        String receiveTimeout = element.getAttribute("timeout");
        if(StringUtils.hasText(receiveTimeout)) {
            builder.addPropertyValue("receiveTimeout", Long.valueOf(receiveTimeout));
        }
        
        Element messageSelectorElement = DomUtils.getChildElementByTagName(element, "selector");
        if (messageSelectorElement != null) {
            Element selectorStringElement = DomUtils.getChildElementByTagName(messageSelectorElement, "value");
            if (selectorStringElement != null) {
                builder.addPropertyValue("messageSelectorString", DomUtils.getTextValue(selectorStringElement));
            }

            Map messageSelector = new HashMap();
            List messageSelectorElements = DomUtils.getChildElementsByTagName(messageSelectorElement, "element");
            for (Iterator iter = messageSelectorElements.iterator(); iter.hasNext();) {
                Element selectorElement = (Element) iter.next();
                messageSelector.put(selectorElement.getAttribute("name"), selectorElement.getAttribute("value"));
            }
            builder.addPropertyValue("messageSelector", messageSelector);
        }

        Element messageElement = DomUtils.getChildElementByTagName(element, "message");
        if (messageElement != null) {
            String schemaValidation = messageElement.getAttribute("schemaValidation");
            if(StringUtils.hasText(schemaValidation)) {
                builder.addPropertyValue("schemaValidation", schemaValidation);
            }
            
            Element xmlDataElement = DomUtils.getChildElementByTagName(messageElement, "data");
            if (xmlDataElement != null) {
                builder.addPropertyValue("messageData", DomUtils.getTextValue(xmlDataElement));
            }

            Element xmlResourceElement = DomUtils.getChildElementByTagName(messageElement, "resource");
            if (xmlResourceElement != null) {
                String filePath = xmlResourceElement.getAttribute("file");
                if (filePath.startsWith("classpath:")) {
                    builder.addPropertyValue("messageResource", new ClassPathResource(filePath.substring("classpath:".length())));
                } else if (filePath.startsWith("file:")) {
                    builder.addPropertyValue("messageResource", new FileSystemResource(filePath.substring("file:".length())));
                } else {
                    builder.addPropertyValue("messageResource", new FileSystemResource(filePath));
                }
            }

            Map setMessageValues = new HashMap();
            List messageValueElements = DomUtils.getChildElementsByTagName(messageElement, "element");
            for (Iterator iter = messageValueElements.iterator(); iter.hasNext();) {
                Element messageValue = (Element) iter.next();
                setMessageValues.put(messageValue.getAttribute("path"), messageValue.getAttribute("value"));
            }
            builder.addPropertyValue("messageElements", setMessageValues);

            List ignoreValues = new ArrayList();
            List ignoreElements = DomUtils.getChildElementsByTagName(messageElement, "ignore");
            for (Iterator iter = ignoreElements.iterator(); iter.hasNext();) {
                Element ignoreValue = (Element) iter.next();
                ignoreValues.add(ignoreValue.getAttribute("path"));
            }
            builder.addPropertyValue("ignoreMessageElements", ignoreValues);

            Map validateValues = new HashMap();
            List validateElements = DomUtils.getChildElementsByTagName(messageElement, "validate");
            if (validateElements.size() > 0) {
                for (Iterator iter = validateElements.iterator(); iter.hasNext();) {
                    Element validateValue = (Element) iter.next();
                    validateValues.put(validateValue.getAttribute("path"), validateValue.getAttribute("value"));
                }
                builder.addPropertyValue("validateMessageElements", validateValues);
            }
            
            Map namespaces = new HashMap();
            List namespaceElements = DomUtils.getChildElementsByTagName(messageElement, "namespace");
            if (namespaceElements.size() > 0) {
                for (Iterator iter = namespaceElements.iterator(); iter.hasNext();) {
                    Element namespaceElement = (Element) iter.next();
                    namespaces.put(namespaceElement.getAttribute("prefix"), namespaceElement.getAttribute("value"));
                }
                builder.addPropertyValue("namespaces", namespaces);
            }
        }

        Element headerElement = DomUtils.getChildElementByTagName(element, "header");
        Map setHeaderValues = new HashMap();
        if (headerElement != null) {
            List elements = DomUtils.getChildElementsByTagName(headerElement, "element");
            for (Iterator iter = elements.iterator(); iter.hasNext();) {
                Element headerValue = (Element) iter.next();
                setHeaderValues.put(headerValue.getAttribute("name"), headerValue.getAttribute("value"));
            }
            builder.addPropertyValue("headerValues", setHeaderValues);
        }

        Element extractElement = DomUtils.getChildElementByTagName(element, "extract");
        Map getMessageValues = new HashMap();
        Map getHeaderValues = new HashMap();
        if (extractElement != null) {
            List headerValueElements = DomUtils.getChildElementsByTagName(extractElement, "header");
            for (Iterator iter = headerValueElements.iterator(); iter.hasNext();) {
                Element headerValue = (Element) iter.next();
                getHeaderValues.put(headerValue.getAttribute("name"), headerValue.getAttribute("variable"));
            }
            builder.addPropertyValue("extractHeaderValues", getHeaderValues);

            List messageValueElements = DomUtils.getChildElementsByTagName(extractElement, "message");
            for (Iterator iter = messageValueElements.iterator(); iter.hasNext();) {
                Element messageValue = (Element) iter.next();
                getMessageValues.put(messageValue.getAttribute("path"), messageValue.getAttribute("variable"));
            }
            builder.addPropertyValue("extractMessageElements", getMessageValues);
        }

        return builder.getBeanDefinition();
    }

}