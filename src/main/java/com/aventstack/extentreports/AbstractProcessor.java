package com.aventstack.extentreports;

import java.util.List;

import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Device;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.SystemEnvInfo;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.context.NamedAttributeContextManager;
import com.aventstack.extentreports.model.service.MediaService;
import com.aventstack.extentreports.model.service.ReportStatsService;
import com.aventstack.extentreports.model.service.TestService;

public abstract class AbstractProcessor extends ReactiveSubject {
    private final List<Test> testList = getReport().getTestList();
    private final NamedAttributeContextManager<Author> authorCtx = getReport().getAuthorCtx();
    private final NamedAttributeContextManager<Category> categoryCtx = getReport().getCategoryCtx();
    private final NamedAttributeContextManager<Device> deviceCtx = getReport().getDeviceCtx();

    private String[] mediaResolverPath;

    @Override
    protected void onTestCreated(Test test) {
        testList.add(test);
        super.onTestCreated(test);
    }

    @Override
    protected void onTestRemoved(Test test) {
        TestService.deleteTest(testList, test);
        super.onTestRemoved(test);
    }

    @Override
    protected void onNodeRemoved(Test node) {
        TestService.deleteTest(testList, node);
        super.onNodeRemoved(node);
    }

    @Override
    protected void onLogCreated(Log log, Test test) {
        super.onLogCreated(log, test);
        log.getExceptions().stream()
            .filter(x -> x.getException() != null)
            .forEach(x -> getReport().getExceptionInfoCtx().addContext(x, test));
    }

    @Override
    protected void onMediaAdded(Media m, Test test) {
        tryResolvePath(m);
        super.onMediaAdded(m, test);
    }

    @Override
    protected void onMediaAdded(Media m, Log log) {
        tryResolvePath(m);
        super.onMediaAdded(m, log);
    }

    private void tryResolvePath(Media m) {
        MediaService.tryResolveMediaPath(m, mediaResolverPath);
    }

    protected void onAuthorAdded(Author x, Test test) {
        authorCtx.addContext(x, test);
    }

    protected void onCategoryAdded(Category x, Test test) {
        categoryCtx.addContext(x, test);
    }

    protected void onDeviceAdded(Device x, Test test) {
        deviceCtx.addContext(x, test);
    }

    @Override
    protected void onFlush() {
        authorCtx.getSet().forEach(x -> x.refresh());
        categoryCtx.getSet().forEach(x -> x.refresh());
        deviceCtx.getSet().forEach(x -> x.refresh());
        ReportStatsService.refreshReportStats(getReport().getStats(), testList);
        super.onFlush();
    }

    protected void onReportLogAdded(String log) {
        getReport().getLogs().add(log);
    }

    protected void onSystemInfoAdded(SystemEnvInfo env) {
        getReport().getSystemEnvInfo().add(env);
    }

    protected void tryesolveMediaPathUsingKnownPaths(String[] knownPath) {
        this.mediaResolverPath = knownPath;
    }
}
