package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
	
	public static String asString( InputStream is ) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[64000];
		int read = 0;
		while ( (read = is.read(buffer)) >= 0 ) out.write(buffer, 0, read);
		is.close();
		String res = new String( out.toByteArray() );
		out.close();
		return res;
	}

	public static String toNextTag(String content, String tagName) {
		int i = content.indexOf("<" + tagName);
		if ( i == 0 ) i = content.indexOf("<" + tagName, i+1);
		if ( i < 0 ) return null;
		return content.substring(i);
	}

	public static String getTagAttribute(String content, String tagName, String attributeName) {
		int i = content.indexOf("<" + tagName);
		if ( i < 0 ) return null;
		
		int j = content.indexOf(">", i+1);
		if ( j < 0 ) return null;
		
		content = content.substring(i, j+1);
		
		Pattern p = Pattern.compile("<"+tagName+" [^>]*"+attributeName+"=['\"]?([^'\"]*)['\"]?[^>]*>");
		Matcher m = p.matcher( content );
		if ( m.matches() ) {
			String res = m.group(1);
			if ( res.startsWith("\"") ) {
				int l = res.lastIndexOf('"', 1);
				if ( l < 0 ) l = res.length();
				return res.substring(1, l);
			}
			return res;
		}
		return null;
	}
}
