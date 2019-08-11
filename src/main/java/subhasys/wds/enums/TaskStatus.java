/**
 * 
 */
package subhasys.wds.enums;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author subhasis
 *
 */
public enum TaskStatus {

	NEW("NEW"), COMPLETED("COMPLETED"), INPROGRESS("INPROGRESS");

	private TaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	private final String taskStatus;

	private static Map<Object, TaskStatus> FORMAT_MAP = Stream.of(TaskStatus.values())
			.collect(Collectors.toMap(taskStat -> taskStat.taskStatus, Function.identity()));

	@JsonCreator
	public static TaskStatus fromString(String taskStatus) {
		return Optional.ofNullable(FORMAT_MAP.get(taskStatus)).orElseThrow(() -> new IllegalArgumentException());
	}
}
