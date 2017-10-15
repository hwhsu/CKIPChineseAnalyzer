# CKIPChineseAnalyzer For Lucene 6.5

### Project Structure

```
├─main
│  └─java
│      └─io
│          └─hwhsu
│              └─ckip
│                      App.java
│                      CKIPChineseAnalyzer.java
│                      CKIPChineseTokenizer.java
```

### Dependencies

- CKIP斷詞服務採用tw.cheyingwu的[CKIP Client](http://ckipclient.sourceforge.net)


### Usage

CKIP帳號請至[中央研究院中文斷詞系統](http://ckipsvr.iis.sinica.edu.tw/)申請
```
CKIPChineseAnalyzer analyzer = new CKIPChineseAnalyzer("140.109.19.104","id","password");
```
