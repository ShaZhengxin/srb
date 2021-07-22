package com.szx.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szx.srb.core.listener.ExcelDictDTOListener;
import com.szx.srb.core.mapper.DictMapper;
import com.szx.srb.core.pojo.dto.ExcelDictDTO;
import com.szx.srb.core.pojo.entity.Dict;
import com.szx.srb.core.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author szx
 * @since 2021-06-25
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    /**事务处理*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class,new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("Excel导入成功");
    }

    @Override
    public List<ExcelDictDTO> listDictData() {

        List<Dict> dictList = baseMapper.selectList(null);
        //创建ExcelDictDTO列表，将Dict列表转换成ExcelDictDTO列表
        ArrayList<ExcelDictDTO> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public List<Dict> listByPatentId(Long parentId) {
        QueryWrapper<Dict> dictQueryMapper = new QueryWrapper<>();
        dictQueryMapper.eq("parent_id",parentId);
        List<Dict> dictList = baseMapper.selectList(dictQueryMapper);
        //填充hasChildren字段
        dictList.forEach(dict -> {
            //判断当前节点是否有子节点，找到当前的dict下级有没有子节点
            Boolean hasChildren = this.hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });
        return dictList;
    }

    /**
     * 判断当前id所在的节点是否有子节点
     * @param id
     * @return
     */
    private Boolean hasChildren(Long id){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(dictQueryWrapper);
        if(count.intValue()>0){
            return true;
        }
        return false;
    }
}
