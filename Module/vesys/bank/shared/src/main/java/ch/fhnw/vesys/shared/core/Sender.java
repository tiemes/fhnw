package ch.fhnw.vesys.shared.core;

public interface Sender {

    Task sendTask(TaskBiFunction taskbifunction, Object... parameters);
}
