package com.qhyj.dao;

import java.util.List;

import com.qhyj.domain.RebateDo;

public class RebateDao extends BaseDao{
	
	public List<RebateDo> getRebateByGid(int gid) {
		return findListBySql(new RebateDo(), "SELECT * FROM T_REBATE WHERE GID=" + gid+" ORDER BY RID");
	}

	public String[] getExpsByGid(int gid) {
		List<RebateDo> list = getRebateByGid(gid);
		if(null==list||list.size()<1) {
			return new String[] {};
		}
		String[] exps = new String[list.size()];
		for(int i=0;i<list.size();i++) {
			exps[i]= list.get(i).getExpression();
		}
		return exps;
	}
	public void addRebate(RebateDo rebateDo) {
		Integer rid = super.insert(rebateDo.getInsertSql());
		rebateDo.setRid(rid);
	}
	public void deleRebateDoByGid(Integer gid) {
		update("DELETE T_REBATE where GID="+gid);
	}
	public void deleRebateDoByRid(Integer rid) {
		update("DELETE T_REBATE where RID="+rid);
	}
}
