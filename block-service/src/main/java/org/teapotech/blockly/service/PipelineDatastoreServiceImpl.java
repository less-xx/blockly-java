package org.teapotech.blockly.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teapotech.blockly.entity.PipelineConfiguration;
import org.teapotech.blockly.repo.PipelineConfigRepo;

@Service
@Transactional
public class PipelineDatastoreServiceImpl implements PipelineDatastoreService {

    public static Logger LOG = LoggerFactory.getLogger(PipelineDatastoreServiceImpl.class);

    @Autowired
    PipelineConfigRepo pipelineConfRepo;

    @Override
    public PipelineConfiguration savePipelineConfig(PipelineConfiguration entity) {
        PipelineConfiguration result = null;
        if (StringUtils.isBlank(entity.getId())) {
            entity.setCreatedTime(new Date());
            result = pipelineConfRepo.save(entity);
            LOG.info("Created pipeline, ID: {}", entity.getId());
        } else {
            entity.setLastUpdatedTime(new Date());
            result = pipelineConfRepo.save(entity);
            LOG.info("Updated pipeline, ID: {}", entity.getId());
        }
        return result;
    }

    @Override
    public Page<PipelineConfiguration> getPipelineConfig(Pageable pageable) {
        return pipelineConfRepo.findAll(pageable);
    }

    @Override
    public PipelineConfiguration findById(String id) {
        return pipelineConfRepo.findById(id).orElse(null);
    }

    @Override
    public PipelineConfiguration findByName(String name) {
        return pipelineConfRepo.findOneByName(name);
    }
}
