package org.opencloudengine.garuda.handler.activity.workflow;

import org.opencloudengine.garuda.util.ExceptionUtils;
import org.opencloudengine.garuda.util.JsonUtils;
import org.opencloudengine.garuda.web.history.TaskHistory;
import org.opencloudengine.garuda.web.history.WorkflowHistory;

import java.util.Date;

public abstract class InterceptorAbstractTask extends AbstractTask {

    @Override
    public void doExecute() throws Exception {

        preRun();
        try {
            runTask();
            updateTaskHistoryData();
            updateTaskHistoryAsFinished();
            fireComplete(instance);
        } catch (Exception ex) {
            stderr = ExceptionUtils.getFullStackTrace(ex);
            updateTaskHistoryData();
            updateTaskHistoryAsFailed();
        }
    }

    protected void preRun() throws Exception {

        /**
         * 워크플로우 히스토리를 업데이트한다.
         */
        this.updateCurrentStep();

        /**
         * 타스크 히스토리를 등록한다.
         */
        this.initTaskHistory();

        /**
         * globalAttributes 저장소에 타스크의 시작 시그널을 남긴다.
         */
        globalAttributes.setTaskStatus(instance, taskId, "RUNNING");
    }

    public abstract void runTask() throws Exception;

    private void initTaskHistory() throws Exception {
        taskHistory = new TaskHistory();
        taskHistory.setTaskId(taskId);
        taskHistory.setTaskName(taskName);
        taskHistory.setStartDate(new Date().getTime());
        taskHistory.setStatus("RUNNING");
        taskHistory.setIdentifier(identifier);
        taskHistory.setWid(wid);

        //instance 에 타스크 히스토리를 등록한다.
        globalAttributes.setTaskHistory(instance, taskId, taskHistory);
    }

    private void updateCurrentStep() throws Exception {
        workflowHistory.setCurrentTaskId(taskId);
        workflowHistory.setCurrentTaskName(taskName);
        instance.set("wh", workflowHistory);
    }

    private void updateTaskHistoryAsFinished() throws Exception {
        long time = new Date().getTime();
        taskHistory.setEndDate(time);
        taskHistory.setDuration(time - taskHistory.getStartDate());
        taskHistory.setStatus("FINISHED");

        //instance 에 타스크 히스토리를 등록한다.
        globalAttributes.setTaskHistory(instance, taskId, taskHistory);

        //globalAttributes 저장소에 타스크의 성공 시그널을 남긴다.
        globalAttributes.setTaskStatus(instance, taskId, "FINISHED");

    }

    public void updateTaskHistoryAsFailed() throws Exception {
        long time = new Date().getTime();
        taskHistory.setEndDate(time);
        taskHistory.setDuration(time - taskHistory.getStartDate());
        taskHistory.setStatus("FAILED");

        //instance 에 타스크 히스토리를 등록한다.
        globalAttributes.setTaskHistory(instance, taskId, taskHistory);

        //globalAttributes 저장소에 타스크의 성공 시그널을 남긴다.
        globalAttributes.setTaskStatus(instance, taskId, "FAILED");
    }

    private void updateTaskHistoryData() throws Exception {
        taskHistory.setStdout(stdout);
        taskHistory.setStderr(stderr);
        taskHistory.setInput(JsonUtils.marshal(inputData));
        taskHistory.setOutput(JsonUtils.marshal(outputData));

        if (inputData != null) {
            globalAttributes.setTaskInput(instance, taskId, inputData);
        }
        if (outputData != null) {
            globalAttributes.setTaskOutput(instance, taskId, outputData);
        }
    }
}
