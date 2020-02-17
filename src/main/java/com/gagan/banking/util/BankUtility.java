package com.gagan.banking.util;

import java.text.DecimalFormat;

import com.gagan.banking.model.AccTransaction;
import com.gagan.banking.model.TransactionType;
import com.gagan.banking.model.TransactionStatusEnum;
import com.gagan.banking.vo.AccTransactionVO;

public class BankUtility {

	public static long numbGen() {
		long numb = (long) (Math.random() * 10000000 * 1000000);
		return numb;
	}

	public static double round(double val) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format(val));
	}

	public static AccTransactionVO populateAccTxVO(AccTransaction accTx) {
		AccTransactionVO accTxVO = null;

		if (accTx != null) {
			accTxVO = new AccTransactionVO();
			accTxVO.setTxTypeName(
					TransactionType.valueOf(accTx.getTxTypeCode()).getTxTypeName());
			accTxVO.setAccNo(accTx.getCustAcct().getAccNo());
			accTxVO.setAffectedBal(accTx.getBalance());
			accTxVO.setAmountAffected(accTx.getAmountAffected());
			accTxVO.setCreatedOn(accTx.getCreatedOn());
			accTxVO.setMessage(accTx.getMessage());
			accTxVO.setTxId(accTx.getTxRef());
			accTxVO.setTxStatus(TransactionStatusEnum.valueOf(accTx.getTxStatus().toUpperCase()).getName());
		}
		return accTxVO;
	}
}
