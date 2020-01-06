package za.co.seamlesspay.v1.util.EmvUtil.iso7816emv;

import za.co.seamlesspay.v1.util.EmvUtil.enums.TagTypeEnum;
import za.co.seamlesspay.v1.util.EmvUtil.enums.TagValueTypeEnum;

public interface ITag {

	public enum Class {
		UNIVERSAL, APPLICATION, CONTEXT_SPECIFIC, PRIVATE
	}

	boolean isConstructed();

	byte[] getTagBytes();

	String getName();

	String getDescription();

	TagTypeEnum getType();

	TagValueTypeEnum getTagValueType();

	Class getTagClass();

	int getNumTagBytes();

}
