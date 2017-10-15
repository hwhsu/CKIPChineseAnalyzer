package io.hwhsu.ckip;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class App {
	

	public static void main(String[] args) {

		String text = "川端康成因其著名小說雪國，獲得諾貝爾文學獎";
		CKIPChineseAnalyzer analyzer = new CKIPChineseAnalyzer("140.109.19.104","id","password");
		TokenStream tokenStream = analyzer.tokenStream("text", new StringReader(text));
		CharTermAttribute termAttr = tokenStream.addAttribute(CharTermAttribute.class);
		TypeAttribute typeAttr = tokenStream.addAttribute(TypeAttribute.class);
		    
		System.out.println("CKIPChineseAnalyzer:");
		System.out.println(getSegTermAndType(tokenStream, termAttr, typeAttr));

	}

	public static String getSegTermAndType(TokenStream tokenStream, CharTermAttribute termAttr, TypeAttribute typeAttr) {

		StringBuffer buffer = new StringBuffer();
		try {
			/*
			 *  在incrementToken()之前必須呼叫reset()
			 *  [The workflow of the new TokenStream API]
			 *  @see http://lucene.apache.org/core/6_5_1/core/index.html 
			 */
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				// 斷詞字符
				buffer.append(termAttr.toString());
				// 斷詞詞性
				buffer.append("[");
				buffer.append(typeAttr.type());
				buffer.append("]");
			}
			tokenStream.end();
			tokenStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
}
