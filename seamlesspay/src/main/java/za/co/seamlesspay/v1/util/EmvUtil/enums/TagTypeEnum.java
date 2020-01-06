package za.co.seamlesspay.v1.util.EmvUtil.enums;

/**
 * Seem EMV Book 3 Annex B
 */
public enum TagTypeEnum {

	/**
	 * The value field of a primitive data object contains a data element for financial transaction interchange
	 */
	PRIMITIVE,
	/**
	 * The value field of a constructed data object contains one or more primitive or constructed data objects. The value field of
	 * a constructed data object is called a template.
	 */
	CONSTRUCTED
}