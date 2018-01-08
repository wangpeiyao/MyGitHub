package com.qhyj.dao;

import java.util.List;

import com.qhyj.domain.RebateDo;

public class RebateDao extends BaseDao{
	
	public List<RebateDo> getRebateByGid(int gid) {
		return findListBySql(new RebateDo(), "SELECT * FROM T_REBATE WHERE GID=" + gid);
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
