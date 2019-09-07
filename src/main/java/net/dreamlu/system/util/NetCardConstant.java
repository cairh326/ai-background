package net.dreamlu.system.util;

/**
 * netCard constant
 * @author Administrator
 */
public class NetCardConstant {
	public static final String PLACE_HOLDER_IP = "{ip}";
	public static final String PLACE_HOLDER_PORT = "{port}";
	public static final String NET_CARD_GET = "http://{ip}:{port}/network/interface/get";
	public static final String NET_CARD_SET = "http://{ip}:{port}/network/interface/set";
	public static final String NET_CARD_SAVE = "http://{ip}:{port}/network/interface/save";
	public static final String IGNORE_METHOD = "loopback";
	public static final String IGNORE_IP = "127.0.0.1";
}
