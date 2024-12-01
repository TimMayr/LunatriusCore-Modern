package com.github.lunatrius.core.reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {
	public static final String MODID = "${modid}";
	public static final String NAME = "${modname}";
	public static final String VERSION = "${modversion}";
	public static final String FORGE = "${version_forge}";
	public static final String MINECRAFT = "${mcversion}";

	public static final Logger logger = LogManager.getLogger(Reference.MODID);
}
