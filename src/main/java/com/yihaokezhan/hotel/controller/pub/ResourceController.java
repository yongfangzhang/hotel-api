package com.yihaokezhan.hotel.controller.pub;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.config.AppConfig;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Resource;
import com.yihaokezhan.hotel.module.service.IResourceService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/pub/resource")
@Slf4j
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AppConfig appConfig;

    @GetMapping("/page")
    @JsonView(V.S.class)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(resourceService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(resourceService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(resourceService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    public R get(@PathVariable String uuid) {
        return R.ok().data(resourceService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @Transactional(rollbackFor = Exception.class)
    public R create(@Validated(AddGroup.class) @RequestBody Resource entity) {
        return R.ok().data(resourceService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @Transactional(rollbackFor = Exception.class)
    public R update(@Validated(UpdateGroup.class) @RequestBody Resource entity) {
        return R.ok().data(resourceService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @Transactional(rollbackFor = Exception.class)
    public R delete(@PathVariable String uuid) {
        return R.ok().data(resourceService.mDelete(uuid));
    }

    @GetMapping("/resource/res/{uuid}")
    public ResponseEntity<org.springframework.core.io.Resource> index(@PathVariable String uuid,
            HttpServletRequest request) {
        Resource resource = resourceService.mGet(uuid);
        if (resource == null) {
            return ResponseEntity.notFound().build();

        }
        try {
            org.springframework.core.io.Resource r = resourceLoader
                    .getResource("file:" + appConfig.getUploadPath() + resource.getVisitPath());
            return ResponseEntity.ok().contentType(getMediaType(request, r, resource)).contentLength(r.contentLength())
                    .body(r);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    private MediaType getMediaType(HttpServletRequest request, org.springframework.core.io.Resource r,
            Resource entity) {
        MediaType mediaType = null;
        try {
            String mediaTypeStr = request.getServletContext().getMimeType(entity.getFileName());
            if (StringUtils.isBlank(mediaTypeStr)) {
                mediaTypeStr = request.getServletContext().getMimeType(r.getFile().getAbsolutePath());
            }
            if (StringUtils.isNotBlank(mediaTypeStr)) {
                mediaType = MediaType.parseMediaType(mediaTypeStr);
            }
        } catch (IOException ex) {
            log.error("Could not determine file type.");
        }

        if (mediaType == null) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        return mediaType;
    }
}
