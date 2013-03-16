package com.googlecode.gwtphonegap.showcase.client.file;

import java.util.LinkedList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.file.DirectoryEntry;
import com.googlecode.gwtphonegap.client.file.DirectoryReader;
import com.googlecode.gwtphonegap.client.file.EntryBase;
import com.googlecode.gwtphonegap.client.file.FileCallback;
import com.googlecode.gwtphonegap.client.file.FileEntry;
import com.googlecode.gwtphonegap.client.file.FileError;
import com.googlecode.gwtphonegap.client.file.FileReader;
import com.googlecode.gwtphonegap.client.file.FileSystem;
import com.googlecode.gwtphonegap.client.file.FileWriter;
import com.googlecode.gwtphonegap.client.file.Flags;
import com.googlecode.gwtphonegap.client.file.ReaderCallback;
import com.googlecode.gwtphonegap.client.file.WriterCallback;
import com.googlecode.gwtphonegap.collection.shared.LightArray;
import com.googlecode.gwtphonegap.showcase.client.ClientFactory;
import com.googlecode.gwtphonegap.showcase.client.NavBaseActivity;
import com.googlecode.gwtphonegap.showcase.client.model.FileDemo;

public class FileActivity extends NavBaseActivity implements FileDisplay.Presenter {

  private FileDisplay display;
  private PhoneGap phoneGap;

  private LightArray<EntryBase> currentEntries;

  private DirectoryEntry currentDir;

  private FileEntry currentFile;

  public FileActivity(ClientFactory clientFactory) {
    super(clientFactory);

    this.display = clientFactory.getFileDisplay();
    phoneGap = clientFactory.getPhoneGap();

    phoneGap.getFile().requestFileSystem(FileSystem.LocalFileSystem_PERSISTENT, 0,
        new FileCallback<FileSystem, FileError>() {

          @Override
          public void onSuccess(FileSystem entry) {
            gotFileSystem(entry);

          }

          @Override
          public void onFailure(FileError error) {

            display.getStatus().setHTML(
                "Failed to request file system with error code: " + error.getErrorCode());

          }
        });
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    display.setPresenter(this);

    panel.setWidget(display);

  }

  private void gotFileSystem(FileSystem fileSystem) {
    listDirectory(fileSystem.getRoot());
  }

  private void listDirectory(final DirectoryEntry directoryEntry) {
    currentDir = directoryEntry;

    display.getStatus().setHTML("Listing '" + directoryEntry.getName() + "");
    DirectoryReader directoryReader = directoryEntry.createReader();

    directoryReader.readEntries(new FileCallback<LightArray<EntryBase>, FileError>() {

      @Override
      public void onSuccess(LightArray<EntryBase> entries) {
        currentEntries = entries;
        display.getStatus().setHTML("Directory '" + directoryEntry.getFullPath() + "'");

        LinkedList<FileDemo> list = new LinkedList<FileDemo>();
        list.add(new FileDemo("../"));

        for (int i = 0; i < entries.length(); i++) {
          EntryBase entryBase = entries.get(i);
          String name;

          if (entryBase.isDirectory()) {
            DirectoryEntry directoryEntry = entryBase.getAsDirectoryEntry();
            name = directoryEntry.getName();
            // display.addEntry(name, true);
            // display.getEntry(i).addClickHandler(new
            // DirectoryClickHandler(i));
          } else {
            FileEntry fileEntry = entryBase.getAsFileEntry();
            name = fileEntry.getName();
            // display.addEntry(name, false);
            // display.getEntry(i).addClickHandler(new
            // FileClickHandler(i));
          }

          list.add(new FileDemo(name));

        }

        display.render(list);

      }

      @Override
      public void onFailure(FileError error) {
        display.getStatus().setHTML("Error while listing '" + directoryEntry.getFullPath() + "'");

      }
    });
  }

  @Override
  public void createFile(String fileName) {
    if (currentDir != null) {

      currentDir.getFile(fileName, new Flags(true, false),
          new FileCallback<FileEntry, FileError>() {

            @Override
            public void onSuccess(FileEntry entry) {
              listDirectory(currentDir);

            }

            @Override
            public void onFailure(FileError error) {
              Window.alert("error");

            }
          });
    }

  }

  @Override
  public void onActionButtonPressed() {
    display.showSelectMenu();

  }

  @Override
  public void overWriteFile() {
    if (display.confirm("Really rewrite file?")) {
      if (currentFile != null) {
        currentFile.createWriter(new FileCallback<FileWriter, FileError>() {

          @Override
          public void onSuccess(FileWriter entry) {
            entry.setOnWriteEndCallback(new WriterCallback<FileWriter>() {

              @Override
              public void onCallback(FileWriter result) {
                Window.alert("file written");

              }
            });

            entry.setOnErrorCallback(new WriterCallback<FileWriter>() {

              @Override
              public void onCallback(FileWriter result) {
                Window.alert("error while writing file");

              }
            });
            entry.write(display.getFileContent().getText());

          }

          @Override
          public void onFailure(FileError error) {
            Window.alert("can not create writer");

          }
        });
      }
    }

  }

  @Override
  public void onEntrySelected(int index) {
    if (index == 0) {
      if (currentDir != null) {
        currentDir.getParent(new FileCallback<DirectoryEntry, FileError>() {

          @Override
          public void onSuccess(DirectoryEntry entry) {
            listDirectory(entry);

          }

          @Override
          public void onFailure(FileError error) {
            Window.alert("error und so !!!!!!!!");

          }
        });
      }
      display.getFileContent().setText("");
      return;
    }
    index = index - 1;
    EntryBase entryBase = currentEntries.get(index);

    if (entryBase.isDirectory()) {
      DirectoryEntry directoryEntry = entryBase.getAsDirectoryEntry();
      listDirectory(directoryEntry);
      display.getFileContent().setText("");
      return;
    }

    if (entryBase.isFile()) {
      FileEntry fileEntry = entryBase.getAsFileEntry();
      readFile(fileEntry);
      display.setSelected(index + 1);
      return;
    }

  }

  private void readFile(final FileEntry fileEntry) {
    FileReader reader = phoneGap.getFile().createReader();

    reader.setOnloadCallback(new ReaderCallback<FileReader>() {

      @Override
      public void onCallback(FileReader result) {

        display.getFileContent().setText(result.getResult());
        currentFile = fileEntry;
      }
    });

    reader.setOnErrorCallback(new ReaderCallback<FileReader>() {

      @Override
      public void onCallback(FileReader result) {
        display.getFileContent().setText("Error while reading file");

      }
    });

    reader.readAsText(fileEntry);
  }

}
