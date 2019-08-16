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
public enum TaskPriority {
	
	NA(0), LOW(1), HIGH(2);
	
	private final int priority;
	
	TaskPriority(int taskPriority) {
		this.priority = taskPriority;
	}
	
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	
	private static Map<Object, TaskPriority> TASK_PRIORITY_MAP = Stream.of(TaskPriority.values())
			.collect(Collectors.toMap(s -> s.priority, Function.identity()));
	
	@JsonCreator
	public static TaskPriority fromString(int priority) {
		return Optional.ofNullable(TASK_PRIORITY_MAP.get(priority)).orElseThrow(() -> new IllegalArgumentException());
	}

}
