package jmscapplications.com.ledscrollindisplay;

import android.support.v4.internal.view.SupportMenu;

public class UnicodeString {
    public static String convert(String str) {
        StringBuffer ostr = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch < ' ' || ch > '~') {
                ostr.append("\\u");
                String hex = Integer.toHexString(str.charAt(i) & SupportMenu.USER_MASK);
                for (int j = 0; j < 4 - hex.length(); j++) {
                    ostr.append("0");
                }
                ostr.append(hex.toLowerCase());
            } else {
                ostr.append(ch);
            }
        }
        return new String(ostr);
    }
}
