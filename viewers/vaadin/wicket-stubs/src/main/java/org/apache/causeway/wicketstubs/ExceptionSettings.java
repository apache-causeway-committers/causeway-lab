package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Args;

public class ExceptionSettings {
    public static final UnexpectedExceptionDisplay SHOW_EXCEPTION_PAGE = new UnexpectedExceptionDisplay("SHOW_EXCEPTION_PAGE");
    public static final UnexpectedExceptionDisplay SHOW_INTERNAL_ERROR_PAGE = new UnexpectedExceptionDisplay("SHOW_INTERNAL_ERROR_PAGE");
    public static final UnexpectedExceptionDisplay SHOW_NO_EXCEPTION_PAGE = new UnexpectedExceptionDisplay("SHOW_NO_EXCEPTION_PAGE");
    private UnexpectedExceptionDisplay unexpectedExceptionDisplay;
    private AjaxErrorStrategy errorHandlingStrategyDuringAjaxRequests;
    private ThreadDumpStrategy threadDumpStrategy;
    private NotRenderableErrorStrategy notRenderableErrorStrategy;

    public ExceptionSettings() {
        this.unexpectedExceptionDisplay = SHOW_EXCEPTION_PAGE;
        this.errorHandlingStrategyDuringAjaxRequests = ExceptionSettings.AjaxErrorStrategy.REDIRECT_TO_ERROR_PAGE;
        this.threadDumpStrategy = ExceptionSettings.ThreadDumpStrategy.THREAD_HOLDING_LOCK;
        this.notRenderableErrorStrategy = ExceptionSettings.NotRenderableErrorStrategy.LOG_WARNING;
    }

    public UnexpectedExceptionDisplay getUnexpectedExceptionDisplay() {
        return this.unexpectedExceptionDisplay;
    }

    public ExceptionSettings setUnexpectedExceptionDisplay(UnexpectedExceptionDisplay unexpectedExceptionDisplay) {
        this.unexpectedExceptionDisplay = unexpectedExceptionDisplay;
        return this;
    }

    public AjaxErrorStrategy getAjaxErrorHandlingStrategy() {
        return this.errorHandlingStrategyDuringAjaxRequests;
    }

    public ExceptionSettings setAjaxErrorHandlingStrategy(AjaxErrorStrategy errorHandlingStrategyDuringAjaxRequests) {
        this.errorHandlingStrategyDuringAjaxRequests = errorHandlingStrategyDuringAjaxRequests;
        return this;
    }

    public ExceptionSettings setThreadDumpStrategy(ThreadDumpStrategy strategy) {
        this.threadDumpStrategy = (ThreadDumpStrategy) Args.notNull(strategy, "strategy");
        return this;
    }

    public ThreadDumpStrategy getThreadDumpStrategy() {
        return this.threadDumpStrategy;
    }

    public NotRenderableErrorStrategy getNotRenderableErrorStrategy() {
        return this.notRenderableErrorStrategy;
    }

    public void setNotRenderableErrorStrategy(NotRenderableErrorStrategy notRenderableErrorStrategy) {
        this.notRenderableErrorStrategy = notRenderableErrorStrategy;
    }

    public static final class UnexpectedExceptionDisplay extends EnumeratedType {
        private static final long serialVersionUID = 1L;

        UnexpectedExceptionDisplay(String name) {
            super(name);
        }
    }

    public static enum AjaxErrorStrategy {
        REDIRECT_TO_ERROR_PAGE,
        INVOKE_FAILURE_HANDLER;

        private AjaxErrorStrategy() {
        }
    }

    public static enum ThreadDumpStrategy {
        NO_THREADS,
        THREAD_HOLDING_LOCK,
        ALL_THREADS;

        private ThreadDumpStrategy() {
        }
    }

    public static enum NotRenderableErrorStrategy {
        LOG_WARNING,
        THROW_EXCEPTION;

        private NotRenderableErrorStrategy() {
        }
    }
}
