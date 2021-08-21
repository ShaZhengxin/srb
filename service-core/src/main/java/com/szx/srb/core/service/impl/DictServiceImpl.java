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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisTemplate redisTemplate;

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
        //首先查询redis中是否存在数据列表
        try {//快捷键Ctrl+Alt+T
            List<Dict> dictList= (List<Dict>) redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if(dictList!=null){
                log.info("从redis中获取数据列表");
                //如果存在则从redis中直接返回数据列表
                return dictList;
            }
        } catch (Exception e) {//一旦redis出现问题，不要抛异常出去，可以从数据库中拿数据
            //捕获异常，将错误跟踪栈字符串追加到日志后面
            log.error("redis服务异常："+ ExceptionUtils.getStackTrace(e));
        }


        //如果不存在则查询数据库
        log.info("从数据库中获取数据列表");
        QueryWrapper<Dict> dictQueryMapper = new QueryWrapper<>();
        dictQueryMapper.eq("parent_id",parentId);
        List<Dict> dictList = baseMapper.selectList(dictQueryMapper);
        //填充hasChildren字段
        dictList.forEach(dict -> {
            //判断当前节点是否有子节点，找到当前的dict下级有没有子节点
            Boolean hasChildren = this.hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });

        try {//如果存数据时redis服务器出现异常，可能报错，数据列表无法返回，所以这里try/catch
            //将数据存入redis
            log.info("将数据存入redis");
            redisTemplate.opsForValue().set("srb:core:dictList:"+parentId,dictList,5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("redis服务异常："+ ExceptionUtils.getStackTrace(e));
        }

        //返回数据列表
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
