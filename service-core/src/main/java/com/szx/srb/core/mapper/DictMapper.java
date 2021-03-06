package com.szx.srb.core.mapper;

import com.szx.srb.core.pojo.dto.ExcelDictDTO;
import com.szx.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author szx
 * @since 2021-06-25
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(List<ExcelDictDTO> list);
}
