package seeurrenamer.main.model;

import java.nio.file.Path;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableView;

/**
 * It is a model class that represents path before and after renamed, this class
 * also can be used as a model for javafx {@link TableView}
 * 
 * @author moch deden
 *
 */
public class PairPath {

	private SimpleObjectProperty<Path> before;
	private SimpleObjectProperty<Path> after;

	public PairPath(Path before, Path after) {
		this.setBefore(before);
		this.setAfter(after);
	}

	public PairPath(Path before) {
		this.setBefore(before);
		this.setAfter(before);
	}

	public void setAfter(Path after) {
		this.after = new SimpleObjectProperty<>(after);
	}

	public void setBefore(Path before) {
		this.before = new SimpleObjectProperty<>(before);
	}

	public Path getBeforeFileName() {
		return this.before.get().getFileName();
	}

	public Path getAfterFileName() {
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
		PairPath other = (PairPath) obj;
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
