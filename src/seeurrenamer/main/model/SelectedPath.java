package seeurrenamer.main.model;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.beans.property.SimpleObjectProperty;

public class SelectedPath {

	private SimpleObjectProperty<Path> before;
	private SimpleObjectProperty<Path> after;

	public SelectedPath(Path before, Path after) {
		this.setBefore(before);
		this.setAfter(after);
	}

	public SelectedPath(Path before) {
		this.setBefore(before);
		this.setAfter(before);
	}

	public void setAfter(Path after) {
		this.after = new SimpleObjectProperty<>(after);
	}

	public void setBefore(Path before) {
		this.before = new SimpleObjectProperty<>(before);
	}

	public Path getBefore() {
		return this.before.get().getFileName();
	}

	public Path getAfter() {
		return this.after.get().getFileName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((after == null) ? 0 : after.hashCode());
		result = prime * result + ((before == null) ? 0 : before.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectedPath other = (SelectedPath) obj;
		if (after.get() == null) {
			if (other.after.get() != null)
				return false;
		} else if (!after.get().equals(other.after.get()))
			return false;
		if (before.get() == null) {
			if (other.before.get() != null)
				return false;
		} else if (!before.get().equals(other.before.get()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.before.get().toString() + " --> "
				+ this.after.get().toString();
	}

	public Path getBeforeFullPath() {
		return this.before.get();
	}

	public Path getAfterFullPath() {
		return this.after.get();
	}

}
