package com.github.lunatrius.core.reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {
	public static final String MODID = "${modid}";
	public static  String NAME = "${modname}";
	public static  String VERSION = "${modversion}";
	public static  String FORGE = "${version_forge}";
	public static  String MINECRAFT = "${mcversion}";

	public static final Logger logger = LogManager.getLogger(Reference.MODID);
}
