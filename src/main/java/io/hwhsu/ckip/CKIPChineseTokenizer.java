package io.hwhsu.ckip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import tw.cheyingwu.ckip.CKIP;
import tw.cheyingwu.ckip.Term;
import tw.cheyingwu.ckip.WordSegmentationService;

/**
   * [CKIPChineseTokenizer]
   * 實作中研院CKIP斷詞服務
   * @author hwhsu
   * @version 1.0
   */
public class CKIPChineseTokenizer extends Tokenizer {
	
	private int termPosition = 0;
	private final CharTermAttribute charAttr = addAttribute(CharTermAttribute.class);
	private final TypeAttribute typeAttr = addAttribute(TypeAttribute.class);
	
	private String host, id, pw;
	private WordSegmentationService ckip;
	
	private ArrayList<Term> tokens = new ArrayList<Term>();
	
	public CKIPChineseTokenizer(String host, String id, String pw) {
		this.host = host;
		this.id = id;
		this.pw = pw;
		ckip = new CKIP(host, 1501, id, pw);
	}

	/* 實作incrementToken()
	 * @see org.apache.lucene.analysis.TokenStream#incrementToken()
	 */
	@Override
	public boolean incrementToken() throws IOException {
		
		// 先清除所有屬性
		clearAttributes();
		
		// 使用ckip斷詞服務
		BufferedReader br = new BufferedReader(input);
		String temp = "", rawText = "";
		try {
			while ((temp = br.readLine()) != null) {
				rawText += temp;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ckip.setRawText(rawText);
		ckip.send();
		for (Term t : ckip.getTerm()) {
			tokens.add(t);
		}
		
		
		Term currentToken = null ; 
		if (termPosition < tokens.size()) {
			currentToken = tokens.get(termPosition);
			// 設定字符值
			charAttr.append(currentToken.getTerm());
			// 設定詞性
			typeAttr.setType(currentToken.getTag());
			termPosition++;
			return true;
		} else {
			return false;
		}
	}
	
	/* 實作reset()
	 */
    @Override
    public void reset() throws IOException {
        super.reset();
        this.termPosition = 0;
    }
    
}
