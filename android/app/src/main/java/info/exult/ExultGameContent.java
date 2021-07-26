package info.exult;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CheckedInputStream;
import java.util.zip.CRC32;

import android.content.Context;
import android.util.Log;

import org.apache.commons.compress.archivers.ArchiveEntry;

class ExultGameContent extends ExultContent {
    private Long m_expectedCrc32;
    private Path m_installPath;
    private Path m_gameRootInArchive;

    public ExultGameContent(String name, Context context, Long expectedCrc32) {
        super("game", name, context);
        m_expectedCrc32 = expectedCrc32;
        m_installPath = context.getFilesDir().toPath().resolve(name).resolve("static");
    }
    
    @Override
    protected boolean identify(Path location, ArchiveEntry archiveEntry, InputStream inputStream) throws IOException {
        // Nothing to do for the terminal call - we've failed to find a valid game if we get that far.
        if (null == archiveEntry) {
            return false;
        }

        // Skip over directories.
        if (archiveEntry.isDirectory()) {
            return false;
        }

        // The Exult engine looks for "initgame.dat" to indicate a valid game.
        Path fullPath = getFullArchivePath(location, archiveEntry);
        if (!fullPath.getFileName().toString().toLowerCase().equals("initgame.dat")) {
            return false;
        }

        // If there is an expected CRC, calculate the entry's CRC to see if it is the game we are expecting.
        if (null != m_expectedCrc32) {
            CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, new CRC32());
            byte[] buffer = new byte[1024];
            while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {}
            long actualCrc32 = checkedInputStream.getChecksum().getValue();
            if (actualCrc32 != m_expectedCrc32) {
                Log.d("ExultGameContent", "CRC mismatch (actual=0x" + Long.toHexString(actualCrc32) + ", expected=0x" + Long.toHexString(m_expectedCrc32) + ")");
                return false;
            }
        }

        // If we got here, we have the game we're looking for.  Note the directory containing it for use by
        // installDestination() and then terminate the search.
        m_gameRootInArchive = fullPath.getParent();
        return true;
    }

    @Override
    protected Path getContentRootInArchive() {
        return m_gameRootInArchive;
    }

    @Override
    protected Path getContentInstallRoot() {
        return m_installPath;
    }
}
