package io.hwhsu.ckip;

import org.apache.lucene.analysis.Analyzer;

/**
   * [CKIPChineseAnalyzer]
   * @author hwhsu
   * @version 1.0
   */
public class CKIPChineseAnalyzer extends Analyzer {
	
	private String host;
	private String id;
	private String pw;
	
	public CKIPChineseAnalyzer(String host, String id, String pw){
		this.id=id;
		this.pw=pw;
		this.host=host;
	}
	
	/* 實作createComponents()
	 * @see org.apache.lucene.analysis.Analyzer#createComponents(java.lang.String)
	 */
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new CKIPChineseTokenizer(this.host, this.id, this.pw));
	}
	
	
}
