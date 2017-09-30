package com.gabrieljadderson.nightplanetgame.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

/**
 * The static-utility class that contains text utility functions.
 *
 * @author Gabriel Jadderson
 */
public final class TextUtils
{
	
	/**
	 * The array of characters used for unpacking text.
	 */
	public static final char CHARACTER_TABLE[] = {' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y',
			'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', '.', ',',
			':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[', ']'};
	
	/**
	 * An array of valid characters.
	 */
	public static final char VALID_CHARACTERS[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '@', '#', '$', '%',
			'^', '&', '*', '(', ')', '-', '+', '=', ':', ';', '.', '>', '<', ',', '"', '[', ']', '|', '?', '/', '`', '_'};
	
	/**
	 * The default constructor.
	 *
	 * @throws UnsupportedOperationException if this class is instantiated.
	 */
	private TextUtils()
	{
		throw new UnsupportedOperationException("This class cannot be " + "instantiated!");
	}
	
	/**
	 * checks All the characters in the given String and validates that they do not appreviate the {@link #VALID_CHARACTERS} char array.
	 * <p>
	 * If infact there is one char in the given String that appreviates, this method will return false.
	 * <p>
	 * If all the chars in the given String match the {@link #VALID_CHARACTERS}, this method will return true;
	 *
	 * @param StringToCheck the string to check
	 * @return true if the given string does consist of {@link #VALID_CHARACTERS}, false otherwise.
	 */
	public static boolean doesContainValidChars(String StringToCheck)
	{
		String l = StringToCheck.toLowerCase();
		String o = l.replaceAll("\\s", "");
		char[] d = o.toCharArray();
		int failSafe = 0;
		for (int i = 0; i < d.length; i++)
		{
			for (int j = 0; j < VALID_CHARACTERS.length; j++)
			{
				if (d[i] == VALID_CHARACTERS[j])
				{
					failSafe++;
					break;
				}
			}
		}
		if (failSafe == d.length) return true;
		else return false;
	}
	
	/**
	 * Converts a {@code long} hash into a string value.
	 *
	 * @param l the long to convert.
	 * @return the converted string.
	 */
	public static String hashToName(long l)
	{
		int i = 0;
		char ac[] = new char[12];
		while (l != 0L)
		{
			long l1 = l;
			l /= 37L;
			ac[11 - i++] = VALID_CHARACTERS[(int) (l1 - l * 37L)];
		}
		return new String(ac, 12 - i, i);
	}
	
	/**
	 * Converts a string to a {@code long} hash value.
	 *
	 * @param s the string to convert.
	 * @return the long hash value.
	 */
	public static long nameToHash(String s)
	{
		long l = 0L;
		for (int i = 0; i < s.length() && i < 12; i++)
		{
			char c = s.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z')
				l += (1 + c) - 65;
			else if (c >= 'a' && c <= 'z')
				l += (1 + c) - 97;
			else if (c >= '0' && c <= '9')
				l += (27 + c) - 48;
		}
		while (l % 37L == 0L && l != 0L)
			l /= 37L;
		return l;
	}
	
	/**
	 * Determines the indefinite article of {@code thing}.
	 *
	 * @param thing the thing to determine for.
	 * @return the indefinite article.
	 */
	public static String determineIndefiniteArticle(String thing)
	{
		char first = thing.toLowerCase().charAt(0);
		boolean vowel = first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u';
		return vowel ? "an" : "a";
	}
	
	/**
	 * Appends the determined indefinite article to {@code thing}.
	 *
	 * @param thing the thing to append.
	 * @return the {@code thing} after the indefinite article has been appended.
	 */
	public static String appendIndefiniteArticle(String thing)
	{
		return determineIndefiniteArticle(thing).concat(" " + thing);
	}
	
	/**
	 * Capitalizes the first character of {@code str}. Any leading or trailing
	 * whitespace in the string should be trimmed before using this method.
	 *
	 * @param str the string to capitalize.
	 * @return the capitalized string.
	 */
	public static String capitalize(String str)
	{
		return str.substring(0, 1).toUpperCase().concat(str.substring(1, str.length()));
	}
	
	/**
	 * formats an unformatted string of color values that look like this:
	 * <p>
	 * <b>140, 52, 61, 1</b>
	 * into a new color instance.
	 *
	 * @param unformattedString
	 * @return a color representation of the given string. e.g
	 * <p>
	 * <b>new Color(140, 52, 61, 1)</b>
	 */
	public static synchronized Color formatColor(String unformattedString)
	{
		float a, r, g, b;
		int ri = StringUtils.indexOf(unformattedString, ",");
		String red = unformattedString.substring(0, ri);
		String s = StringUtils.replace(unformattedString, red + ", ", "");
		int gi = StringUtils.indexOf(s, ",");
		String green = s.substring(0, gi);
		String s2 = StringUtils.replace(s, green + ", ", "");
		int bi = StringUtils.indexOf(s2, ",");
		String blue = s2.substring(0, bi);
		String alpha = StringUtils.replace(s2, blue + ", ", "");
		a = Float.parseFloat(alpha);
		r = Float.parseFloat(red);
		g = Float.parseFloat(green);
		b = Float.parseFloat(blue);
		return new Color(r, g, b, a);
	}
	
	/**
	 * Formats {@code price} into K, million, or its default value.
	 *
	 * @param price the price to format.
	 * @return the newly formatted price.
	 */
	public static String formatPrice(int price)
	{
		if (price >= 1000 && price < 1000000)
		{
			return "(" + (price / 1000) + "K)";
		} else if (price >= 1000000)
		{
			return "(" + (price / 1000000) + " million)";
		}
		return Integer.toString(price);
	}
	
	// Centering
	//-----------------------------------------------------------------------
	
	/**
	 * Centers a String in a larger String of size <code>size</code>
	 * using the space character (' ').
	 * <p>
	 * If the size is less than the String length, the String is returned.
	 * A <code>null</code> String returns <code>null</code>.
	 * A negative size is treated as zero.
	 * <p>
	 * Equivalent to <code>center(str, size, " ")</code>.
	 * <p>
	 * <pre>
	 * StringUtils.center(null, *)   = null
	 * StringUtils.center("", 4)     = "    "
	 * StringUtils.center("ab", -1)  = "ab"
	 * StringUtils.center("ab", 4)   = " ab "
	 * StringUtils.center("abcd", 2) = "abcd"
	 * StringUtils.center("a", 4)    = " a  "
	 * </pre>
	 *
	 * @param str  the String to center, may be null
	 * @param size the int size of new String, negative treated as zero
	 * @return centered String, <code>null</code> if null String input
	 */
	public static String center(String str, int size)
	{
		return center(str, size, ' ');
	}
	
	/**
	 * Centers a String in a larger String of size <code>size</code>.
	 * Uses a supplied character as the value to pad the String with.
	 * <p>
	 * If the size is less than the String length, the String is returned.
	 * A <code>null</code> String returns <code>null</code>.
	 * A negative size is treated as zero.
	 * <p>
	 * <pre>
	 * StringUtils.center(null, *, *)     = null
	 * StringUtils.center("", 4, ' ')     = "    "
	 * StringUtils.center("ab", -1, ' ')  = "ab"
	 * StringUtils.center("ab", 4, ' ')   = " ab"
	 * StringUtils.center("abcd", 2, ' ') = "abcd"
	 * StringUtils.center("a", 4, ' ')    = " a  "
	 * StringUtils.center("a", 4, 'y')    = "yayy"
	 * </pre>
	 *
	 * @param str     the String to center, may be null
	 * @param size    the int size of new String, negative treated as zero
	 * @param padChar the character to pad the new String with
	 * @return centered String, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String center(String str, int size, char padChar)
	{
		if (str == null || size <= 0)
		{
			return str;
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0)
		{
			return str;
		}
		str = StringUtils.leftPad(str, strLen + pads / 2, padChar);
		str = StringUtils.rightPad(str, size, padChar);
		return str;
	}
	
	/**
	 * Centers a String in a larger String of size <code>size</code>.
	 * Uses a supplied String as the value to pad the String with.
	 * <p>
	 * If the size is less than the String length, the String is returned.
	 * A <code>null</code> String returns <code>null</code>.
	 * A negative size is treated as zero.
	 * <p>
	 * <pre>
	 * StringUtils.center(null, *, *)     = null
	 * StringUtils.center("", 4, " ")     = "    "
	 * StringUtils.center("ab", -1, " ")  = "ab"
	 * StringUtils.center("ab", 4, " ")   = " ab"
	 * StringUtils.center("abcd", 2, " ") = "abcd"
	 * StringUtils.center("a", 4, " ")    = " a  "
	 * StringUtils.center("a", 4, "yz")   = "yayz"
	 * StringUtils.center("abc", 7, null) = "  abc  "
	 * StringUtils.center("abc", 7, "")   = "  abc  "
	 * </pre>
	 *
	 * @param str    the String to center, may be null
	 * @param size   the int size of new String, negative treated as zero
	 * @param padStr the String to pad the new String with, must not be null or empty
	 * @return centered String, <code>null</code> if null String input
	 * @throws IllegalArgumentException if padStr is <code>null</code> or empty
	 */
	public static String center(String str, int size, String padStr)
	{
		if (str == null || size <= 0)
		{
			return str;
		}
		if (StringUtils.isEmpty(padStr))
		{
			padStr = " ";
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0)
		{
			return str;
		}
		str = StringUtils.leftPad(str, strLen + pads / 2, padStr);
		str = StringUtils.rightPad(str, size, padStr);
		return str;
	}
	
	public static Vector2 getCoordsForACenteredString(String s, float w, float h, BitmapFont font)
	{
		GlyphLayout metrics = new GlyphLayout(font, s);
		float x = (w - metrics.width / 2f);
		float y = (font.getAscent() + (h - (font.getAscent() + font.getDescent())) / 2f);
		return new Vector2(x, y);
//        g.drawString(s, x, y);
	}
	
	// CALCULATIONS
	public static String getLongestString(String[] array, Graphics g)
	{
		FontMetrics fm = g.getFontMetrics();
		int maxLength = 0;
		String longestString = null;
		for (String s : array)
		{
			if (fm.stringWidth(s) > maxLength)
			{
				maxLength = fm.stringWidth(s);
				longestString = s;
			}
		}
		return longestString;
	}
}
