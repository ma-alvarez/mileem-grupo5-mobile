/*******************************************************************************
 * Copyright 2012-present Pixate, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * Copyright (c) 2012 Pixate, Inc. All rights reserved.
 */
package com.pixate.freestyle.parsing;

/**
 * Pixate parser exception.
 */
public class PXParserException extends RuntimeException {

	private static final long serialVersionUID = 3479312562533858068L;

	/**
	 * Constructs a new PXSSParserException with a given message.
	 * 
	 * @param message
	 */
	public PXParserException(String message) {
		super(message);
	}

}
