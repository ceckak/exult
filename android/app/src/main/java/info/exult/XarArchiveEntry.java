package info.exult;

import com.sprylab.xar.XarEntry;
import java.util.Date;
import org.apache.commons.compress.archivers.ArchiveEntry;

class XarArchiveEntry implements ArchiveEntry {
  private final XarEntry m_xarEntry;

  XarArchiveEntry(XarEntry xarEntry) {
    m_xarEntry = xarEntry;
  }

  @Override
  public String getName() {
    return m_xarEntry.getName();
  }

  @Override
  public long getSize() {
    return m_xarEntry.getSize();
  }

  @Override
  public boolean isDirectory() {
    return m_xarEntry.isDirectory();
  }

  @Override
  public Date getLastModifiedDate() {
    return m_xarEntry.getTime();
  }
}
