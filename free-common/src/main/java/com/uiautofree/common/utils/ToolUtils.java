package com.uiautofree.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.List;

import static com.uiautofree.common.constant.CommonConstant.IS_INTEGER_PATTERN;

@Component
@Slf4j
public class ToolUtils {
    private static final int COMMON_SLEEP = 100;
    private static final int MAX_LENGTH = 1000;
    private static final InetAddressValidator VALIDATOR = InetAddressValidator.getInstance();

    public String getIpAddress() {
        try {
            java.util.Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                List<InterfaceAddress> addressList = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress address : addressList) {
                    InetAddress ip = address.getAddress();
                    if (ip.getHostAddress() != null && !ip.getHostAddress().startsWith("127") && VALIDATOR.isValidInet4Address(ip.getHostAddress())) {
                        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                        if (network != null) {
                            byte[] mac = network.getHardwareAddress();
                            StringBuilder stringBuilder = new StringBuilder();
                            log.info("[getIpAddress] {}", mac);
                            if (mac == null) {
                                log.error("[getIpAddress] 无法获取 {} 的 mac {}", ip, mac);
                            } else {
                                return ip.getHostAddress();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error("[getIpAddress] get error {}", e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    public String getOsName() {
        return System.getProperty("os.name").toLowerCase();
    }

    public String getMacAddress() {
        try {
            java.util.Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = enumeration.nextElement();
                List<InterfaceAddress> addressList = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress address : addressList) {
                    InetAddress ip = address.getAddress();
                    if (ip.getHostAddress() != null && !ip.getHostAddress().startsWith("127") && VALIDATOR.isValidInet4Address(ip.getHostAddress())) {
                        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                        if (network != null) {
                            byte[] mac = network.getHardwareAddress();
                            StringBuilder sb = new StringBuilder();
                            log.info("[getMacAddress] : {}", mac);
                            if (mac == null) {
                                log.error("[getMacAddress] : error, mac get error null");
                            } else {
                                for (int i = 0; i < mac.length; i++) {
                                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                                }
                                return sb.toString();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("[getMacAddress] : {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String readLocalFileToString(InputStream resFileStream, String resFilePath) throws Exception {
        String str = "";
        try {
            int size = resFileStream.available();
            byte[] buffers = new byte[size];
            resFileStream.read(buffers);
            resFileStream.close();
            str = new String(buffers, "GB2312");
        } catch (Exception e) {
            log.error("[readLocalFileToString] error {}", e.getMessage());
            e.printStackTrace();
        }
        return str;
    }

    public String subStr(String str) {
        if (str != null && str.length() <= MAX_LENGTH) {
            return str;
        } else {
            return str.substring(0, MAX_LENGTH) + "......";
        }
    }

    public String subStr(Object[] arr) {
        String str = Arrays.toString(arr);
        return subStr(str);
    }

    public Boolean isInteger(String str) {
        return IS_INTEGER_PATTERN.matcher(str).matches();
    }
}
