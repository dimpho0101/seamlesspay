/*
 * Copyright (C) 2013 MILLAU Julien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.co.seamlesspay.v1.util.EmvUtil.model;


import za.co.seamlesspay.v1.util.EmvUtil.model.enums.ServiceCode1Enum;
import za.co.seamlesspay.v1.util.EmvUtil.model.enums.ServiceCode2Enum;
import za.co.seamlesspay.v1.util.EmvUtil.model.enums.ServiceCode3Enum;
import za.co.seamlesspay.v1.util.EmvUtil.utils.BitUtils;
import za.co.seamlesspay.v1.util.EmvUtil.utils.BytesUtils;
import za.co.seamlesspay.v1.util.EmvUtil.utils.EnumUtils;

/**
 * Track 2 service
 * 
 * @author MILLAU julien
 * 
 */
public class Service extends AbstractData{

	/**
	 * Generated serial UID
	 */
	private static final long serialVersionUID = 5154895810563519768L;

	/**
	 * Service code 1
	 */
	private ServiceCode1Enum serviceCode1;

	/**
	 * Service code 2
	 */
	private ServiceCode2Enum serviceCode2;

	/**
	 * Service code 3
	 */
	private ServiceCode3Enum serviceCode3;

	/**
	 * Constructor with service bytes array parameter
	 * 
	 * @param pData
	 *            service as byte array
	 */
	public Service(final String pData) {
		if (pData != null && pData.length() == 3) {
			BitUtils bit = new BitUtils(BytesUtils.fromString(CommonsUtils.rightPad(pData, 4, "0")));
			serviceCode1 = EnumUtils.getValue(bit.getNextInteger(4), ServiceCode1Enum.class);
			serviceCode2 = EnumUtils.getValue(bit.getNextInteger(4), ServiceCode2Enum.class);
			serviceCode3 = EnumUtils.getValue(bit.getNextInteger(4), ServiceCode3Enum.class);
		}
	}

	/**
	 * Method used to get the field serviceCode1
	 * 
	 * @return the serviceCode1
	 */
	public ServiceCode1Enum getServiceCode1() {
		return serviceCode1;
	}

	/**
	 * Method used to get the field serviceCode2
	 * 
	 * @return the serviceCode2
	 */
	public ServiceCode2Enum getServiceCode2() {
		return serviceCode2;
	}

	/**
	 * Method used to get the field serviceCode3
	 * 
	 * @return the serviceCode3
	 */
	public ServiceCode3Enum getServiceCode3() {
		return serviceCode3;
	}

}
