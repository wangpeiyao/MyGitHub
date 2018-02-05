package cn.bh.jc;

import java.util.List;

/**
 * 差异文件接口
 * 
 * @author liubq
 * @since 2018年1月4日
 */
public interface IListDiffOper {

	/**
	 * 列出所有变化文件
	 * 
	 * @return 变化文件列表
	 * @throws Exception
	 */
	public List<String> listChangeFile() throws Exception;
}
