package com.kh.spring.data.model.dto.covid19;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	 private int seq;                // 게시글번호    		SEQ
     private String createDt;        // 등록일시분초    	CREATE_DT
     private int deathCnt;           // 사망자 수       	DEATH_CNT
     private int defCnt;             // 확진자 수        	DEF_CNT
     private String gubun;           // 시도명(한글)    	GUBUN
     private String gubunCn;         // 시도명(중국어)		GUBUN_CN
     private String gubunEn;         // 시도명(영어)    	GUBUN_EN
     private int incDec;             // 전일대비 증감 수    INC_DEC
     private int isolClearCnt;       // 격리 해제 수    	ISOL_CLEAR_CNT
     private int isolIngCnt;         // 격리중 환자수    	ISOL_ING_CNT
     private int localOccCnt;        // 지역발생 수 		LOCAL_OCC_CNT    
     private int overFlowCnt;        // 해외유입 수 		OVER_FLOW_CNT    
     private String qurRate;         // 10만명당 발생률    QUR_RATE
     private String stdDay;          // 기준일시        	STD_DAY
     private String updateDt;        // 수정일시분초    	UPDATE_DT
	

}
