import sys
import traceback

def get_thread_dump():
    dump = []
    for thread_id, frame in sys._current_frames().items():
        stack = traceback.format_stack(frame)
        dump.append(f"Thread ID: {thread_id}\n{''.join(stack)}")
    return '\n'.join(dump)

# Example usage
thread_dump = get_thread_dump()
print(thread_dump)
