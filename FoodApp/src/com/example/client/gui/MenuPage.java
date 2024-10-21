package com.example.client.gui;

import java.util.List;

import com.example.client.service.MenuService;
import com.example.client.service.MenuServiceAsync;
import com.example.domain.MenuItem;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuPage extends Composite {
    private final MenuServiceAsync menuService = GWT.create(MenuService.class);
    private List<MenuItem> menuItems;
    private Label deleteItemHeader;
    public MenuPage() {
        VerticalPanel menuPanel = new VerticalPanel();
        menuPanel.setStyleName("adminMenuPanel");

        final Grid menuGrid = new Grid(1, 4);
        menuGrid.setStyleName("adminMenuGrid");
        menuGrid.setText(0, 0, "Available");
        menuGrid.setText(0, 1, "Food Item");
        menuGrid.setText(0, 2, "Price");
        deleteItemHeader=new Label("Delete Item");
        deleteItemHeader.setVisible(false);
        menuGrid.setWidget(0, 3, deleteItemHeader);
        menuPanel.add(menuGrid);
        RootPanel.get().add(menuPanel);

        fetchMenuItems(menuGrid);

        HorizontalPanel buttonPanel = new HorizontalPanel();
        final Button editButton = new Button("Edit");
        final Button saveButton = new Button("Save");
        final Button newButton = new Button("New");
        saveButton.setEnabled(false);
        editButton.setStyleName("adminMenuButton");
        saveButton.setStyleName("adminMenuButton");
        newButton.setStyleName("adminMenuButton");
        newButton.setVisible(false);
        buttonPanel.setStyleName("adminMenuButtonPanel");
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(newButton);

        menuPanel.add(buttonPanel);
        initWidget(menuPanel);

        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                enableEditing(menuGrid);
                saveButton.setEnabled(true);
                editButton.setEnabled(false);
                newButton.setVisible(true);
                deleteItemHeader.setVisible(true);
            }
        });

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                disableEditing(menuGrid);
                saveButton.setEnabled(false);
                editButton.setEnabled(true);
                newButton.setVisible(false);
                deleteItemHeader.setVisible(false);
            }
        });

        newButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                showNewItemDialog(menuGrid);
            }
        });
    }

    private void fetchMenuItems(final Grid menuGrid) {
        menuService.getAllMenuItems(new AsyncCallback<List<MenuItem>>() {
            @Override
            public void onFailure(Throwable caught) {
                // Handle failure
            }

            @Override
            public void onSuccess(List<MenuItem> result) {
                if (result != null) {
                    menuItems = result;
                    for (int i = 0; i < result.size(); i++) {
                        final MenuItem item = result.get(i);

                        CheckBox availableCheckBox = new CheckBox();
                        availableCheckBox.setValue(item.isAvailable());
                        availableCheckBox.addClickHandler(new AvailabilityClickHandler(item));
                        availableCheckBox.setEnabled(false);
                        menuGrid.resizeRows(i + 2);
                        menuGrid.setWidget(i + 1, 0, availableCheckBox);

                        Label foodItemLabel = new Label(item.getItemName());
                        menuGrid.setWidget(i + 1, 1, foodItemLabel);

                        TextBox priceTextBox = new TextBox();
                        priceTextBox.setText(Double.toString(item.getPrice()));
                        priceTextBox.setEnabled(false);
                        menuGrid.setWidget(i + 1, 2, priceTextBox);

                        // Add delete button (initially hidden)
                        Button deleteButton = new Button("Delete");
                        deleteButton.setVisible(false); // Hide initially
                        deleteButton.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                deleteMenuItem(item, menuGrid);
                            }
                        });
                        menuGrid.setWidget(i + 1, 3, deleteButton);
                    }
                }
            }
        });
    }

    private void enableEditing(Grid menuGrid) {
        for (int i = 1; i < menuGrid.getRowCount(); i++) {
            TextBox priceTextBox = (TextBox) menuGrid.getWidget(i, 2);
            priceTextBox.setEnabled(true);

            CheckBox availableCheckBox = (CheckBox) menuGrid.getWidget(i, 0);
            availableCheckBox.setEnabled(true);


            Button deleteButton = (Button) menuGrid.getWidget(i, 3);
            deleteButton.setVisible(true);
        }
    }

    private void disableEditing(Grid menuGrid) {
        for (int i = 1; i < menuGrid.getRowCount(); i++) {
            TextBox priceTextBox = (TextBox) menuGrid.getWidget(i, 2);
            priceTextBox.setEnabled(false);

            CheckBox availableCheckBox = (CheckBox) menuGrid.getWidget(i, 0);
            availableCheckBox.setEnabled(false);


            Button deleteButton = (Button) menuGrid.getWidget(i, 3);
            deleteButton.setVisible(false);
        }
    }

    private class AvailabilityClickHandler implements ClickHandler {
        private MenuItem item;

        public AvailabilityClickHandler(MenuItem item) {
            this.item = item;
        }

        @Override
        public void onClick(ClickEvent event) {
            CheckBox checkBox = (CheckBox) event.getSource();
            item.setAvailable(checkBox.getValue());
            menuService.updateMenuItem(item, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    // Handle failure
                }

                @Override
                public void onSuccess(Void result) {
                    // Handle success
                }
            });
        }
    }

    private void showNewItemDialog(final Grid menuGrid) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Add New Menu Item");
        dialogBox.setAnimationEnabled(true);

        VerticalPanel dialogPanel = new VerticalPanel();
        dialogPanel.setSpacing(5);

        final TextBox itemNameTextBox = new TextBox();
        itemNameTextBox.getElement().setAttribute("placeholder", "Item Name");
        final TextBox descriptionTextBox = new TextBox();
        descriptionTextBox.getElement().setAttribute("placeholder", "Description");
        final TextBox priceTextBox = new TextBox();
        priceTextBox.getElement().setAttribute("placeholder", "Price");
        final TextBox imageUrlTextBox = new TextBox();
        imageUrlTextBox.getElement().setAttribute("placeholder", "Image URL");

        dialogPanel.add(new Label("Item Name:"));
        dialogPanel.add(itemNameTextBox);
        dialogPanel.add(new Label("Description:"));
        dialogPanel.add(descriptionTextBox);
        dialogPanel.add(new Label("Price:"));
        dialogPanel.add(priceTextBox);
        dialogPanel.add(new Label("Image URL:"));
        dialogPanel.add(imageUrlTextBox);

        HorizontalPanel buttonPanel = new HorizontalPanel();
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialogPanel.add(buttonPanel);

        dialogBox.setWidget(dialogPanel);
        dialogBox.center();

        okButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String itemName = itemNameTextBox.getText();
                String description = descriptionTextBox.getText();
                double price = Double.parseDouble(priceTextBox.getText());
                String imageUrl = imageUrlTextBox.getText();

                final MenuItem newItem = new MenuItem();
                newItem.setItemName(itemName);
                newItem.setDescription(description);
                newItem.setPrice(price);
                newItem.setImageURL(imageUrl);
                newItem.setAvailable(true);
                menuService.addMenuItem(newItem, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        
                    }

                    @Override
                    public void onSuccess(Void result) {
                        addNewItemToGrid(menuGrid, newItem);
                        dialogBox.hide();
                    }
                });
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });
    }

    private void addNewItemToGrid(final Grid menuGrid,final MenuItem newItem) {
        int rowCount = menuGrid.getRowCount();
        menuGrid.resizeRows(rowCount + 1);

        CheckBox availableCheckBox = new CheckBox();
        availableCheckBox.setValue(newItem.isAvailable());
        availableCheckBox.setEnabled(false);
        menuGrid.setWidget(rowCount, 0, availableCheckBox);

        Label foodItemLabel = new Label(newItem.getItemName());
        menuGrid.setWidget(rowCount, 1, foodItemLabel);

        TextBox priceTextBox = new TextBox();
        priceTextBox.setText(Double.toString(newItem.getPrice()));
        priceTextBox.setEnabled(false);
        menuGrid.setWidget(rowCount, 2, priceTextBox);


        Button deleteButton = new Button("Delete");
        deleteButton.setVisible(false); 
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                deleteMenuItem(newItem, menuGrid);
            }
        });
        menuGrid.setWidget(rowCount, 3, deleteButton); 
    }

    private void deleteMenuItem(final MenuItem item, final Grid menuGrid) {
        menuService.deleteMenuItem(item, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Void result) {
                removeItemFromGrid(item, menuGrid);
            }
        });
    }

    private void removeItemFromGrid(MenuItem item, Grid menuGrid) {
        int indexToRemove = -1;
        for (int i = 1; i < menuGrid.getRowCount(); i++) { // Start from 1 to skip the header
            if (((Label) menuGrid.getWidget(i, 1)).getText().equals(item.getItemName())) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            menuItems.remove(indexToRemove - 1);
            menuGrid.removeRow(indexToRemove);
        }
    }
}

//package com.example.client.gui;
//
//import java.util.List;
//
//import com.example.client.service.MenuService;
//import com.example.client.service.MenuServiceAsync;
//import com.example.domain.MenuItem;
//import com.google.gwt.core.shared.GWT;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.CheckBox;
//import com.google.gwt.user.client.ui.Composite;
//import com.google.gwt.user.client.ui.DialogBox;
//import com.google.gwt.user.client.ui.FlowPanel;
//import com.google.gwt.user.client.ui.Grid;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.TextBox;
//import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.user.client.ui.Widget;
//
//public class MenuPage extends Composite{
//	private final MenuServiceAsync menuService=GWT.create(MenuService.class);
//	private List<MenuItem> menuItems;
//	public MenuPage() {
//		VerticalPanel menuPanel=new VerticalPanel();
//		menuPanel.setStyleName("adminMenuPanel");
//
//		
//		final Grid menuGrid=new Grid(1,3);
//		menuGrid.setStyleName("adminMenuGrid");
//		menuGrid.setText(0, 0, "Available");
//		menuGrid.setText(0, 1, "Food Item");
//		menuGrid.setText(0, 2, "Price");
//		
//
//		menuPanel.add(menuGrid);
//		RootPanel.get().add(menuPanel);
//		
//		fetchMenuItems(menuGrid);
//		
//		HorizontalPanel buttonPanel=new HorizontalPanel();
//		final Button editButton=new Button("Edit");
//		final Button saveButton=new Button("Save");
//		final Button newButton=new Button("new");
//		saveButton.setEnabled(false);
//		editButton.setStyleName("adminMenuButton");
//		saveButton.setStyleName("adminMenuButton");
//		newButton.setStyleName("adminMenuButton");
//		newButton.setVisible(false);
//		buttonPanel.setStyleName("adminMenuButtonPanel");
//		buttonPanel.add(editButton);
//		buttonPanel.add(saveButton);
//		buttonPanel.add(newButton);
//		
//		menuPanel.add(buttonPanel);
//		initWidget(menuPanel);
//		
//		editButton.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				enableEditing(menuGrid);
//				saveButton.setEnabled(true);
//				editButton.setEnabled(false);
//				newButton.setVisible(true);
//				
//			}
//			
//		});
//		saveButton.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				disableEditing(menuGrid);
//				saveButton.setEnabled(false);
//				editButton.setEnabled(true);
//				newButton.setVisible(false);
//			}
//			
//		});
//		newButton.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				showNewItemDialog(menuGrid);
//			}
//			
//		});
//    }
//	private void fetchMenuItems(final Grid menuGrid){
//		menuService.getAllMenuItems(new AsyncCallback<List<MenuItem>>(){
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//			@Override
//			public void onSuccess(List<MenuItem> result) {
//				// TODO Auto-generated method stub
//				if(result!=null){
//					menuItems=result;
//					for(int i=0;i<result.size();i++){
//						MenuItem item=result.get(i);
//						
//						CheckBox availableCheckBox=new CheckBox();
//						availableCheckBox.setValue(item.isAvailable());
//						availableCheckBox.addClickHandler(new AvailabilityClickHandler(item));
//						availableCheckBox.setEnabled(false);
//						menuGrid.resizeRows(i+2);
//						menuGrid.setWidget(i+1, 0, availableCheckBox);
//						
//						Label foodItemLabel=new Label(item.getItemName());
//						menuGrid.setWidget(i+1,1,foodItemLabel);
//						
//						TextBox priceTextBox=new TextBox();
//						priceTextBox.setText(Double.toString(item.getPrice()));
//						priceTextBox.setEnabled(false);
//						menuGrid.setWidget(i+1, 2, priceTextBox);
//						
//					}
//				}
//			}
//			
//		});
//	}
//	private void enableEditing(Grid menuGrid){
//		for(int i=1;i<menuGrid.getRowCount();i++){
//			TextBox priceTextBox=(TextBox)menuGrid.getWidget(i, 2);
//			priceTextBox.setEnabled(true);
//			
//			CheckBox availableCheckBox=(CheckBox) menuGrid.getWidget(i, 0);
//			availableCheckBox.setEnabled(true);
//		}
//	}
//	private void disableEditing(Grid menuGrid) {
//        for (int i = 1; i < menuGrid.getRowCount(); i++) {
//            TextBox priceTextBox = (TextBox) menuGrid.getWidget(i, 2);
//            priceTextBox.setEnabled(false);
//            
//            CheckBox availableCheckBox=(CheckBox) menuGrid.getWidget(i, 0);
//			availableCheckBox.setEnabled(false);
//        }
//    }
//	
//	private class AvailabilityClickHandler implements ClickHandler{
//		private MenuItem item;
//		public AvailabilityClickHandler(MenuItem item){
//			this.item=item;
//		}
//		@Override
//		public void onClick(ClickEvent event) {
//			// TODO Auto-generated method stub
//			CheckBox checkBox=(CheckBox) event.getSource();
//			item.setAvailable(checkBox.getValue());
//			menuService.updateMenuItem(item, new AsyncCallback<Void>(){
//
//				@Override
//				public void onFailure(Throwable caught) {
//					// TODO Auto-generated method stub
//					
//				}
//
//				@Override
//				public void onSuccess(Void result) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//			});
//		}
//		
//	}
//	private void showNewItemDialog(final Grid menuGrid){
//		final DialogBox dialogBox=new DialogBox();
//		dialogBox.setText("Add New Menu Item");
//		dialogBox.setAnimationEnabled(true);
//		
//		VerticalPanel dialogPanel=new VerticalPanel();
//		dialogPanel.setSpacing(5);
//		
//		// Item details inputs
//        final TextBox itemNameTextBox = new TextBox();
//        itemNameTextBox.getElement().setAttribute("placeholder", "Item Name");
//        final TextBox descriptionTextBox = new TextBox();
//        descriptionTextBox.getElement().setAttribute("placeholder", "description");        
//        final TextBox priceTextBox = new TextBox();
//        priceTextBox.getElement().setAttribute("placeholder", "price");
//        final TextBox imageUrlTextBox = new TextBox();
//        imageUrlTextBox.getElement().setAttribute("placeholder", "imageUrl");
//        
//        dialogPanel.add(new Label("Item Name:"));
//        dialogPanel.add(itemNameTextBox);
//        dialogPanel.add(new Label("Description:"));
//        dialogPanel.add(descriptionTextBox);
//        dialogPanel.add(new Label("Price:"));
//        dialogPanel.add(priceTextBox);
//        dialogPanel.add(new Label("Image URL:"));
//        dialogPanel.add(imageUrlTextBox);
//        
//        HorizontalPanel buttonPanel = new HorizontalPanel();
//        Button okButton = new Button("OK");
//        Button cancelButton = new Button("Cancel");
//        buttonPanel.add(okButton);
//        buttonPanel.add(cancelButton);
//        dialogPanel.add(buttonPanel);
//
//        dialogBox.setWidget(dialogPanel);
//        dialogBox.center();
//        
//        okButton.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				String itemName=itemNameTextBox.getText();
//				String description=descriptionTextBox.getText();
//				double price=Double.parseDouble(priceTextBox.getText());
//				String imageUrl=imageUrlTextBox.getText();
//				
//				final MenuItem newItem=new MenuItem();
//				newItem.setItemName(itemName);
//				newItem.setDescription(description);
//				newItem.setPrice(price);
//				newItem.setImageURL(imageUrl);
//				newItem.setAvailable(true);
//				menuService.addMenuItem(newItem, new AsyncCallback<Void>() {
//		            @Override
//		            public void onFailure(Throwable caught) {
//		                // Handle failure scenario, e.g., show an error message
//		            }
//
//		            @Override
//		            public void onSuccess(Void result) {
//		                // Update the grid to display the new item
//		                addNewItemToGrid(menuGrid, newItem);
//		                dialogBox.hide();
//		            }
//		        });
//			}
//        	
//        });
//        cancelButton.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				dialogBox.hide();
//			}
//        	
//        });
//	}
//	
//	private void addNewItemToGrid(Grid menuGrid,MenuItem newItem){
//		int newRow=menuGrid.getRowCount();
//		menuGrid.resizeRows(newRow+1);
//		
//		CheckBox availableCheckBox = new CheckBox();
//        availableCheckBox.setValue(newItem.isAvailable());
//        menuGrid.setWidget(newRow, 0, availableCheckBox);
//
//        Label foodItemLabel = new Label(newItem.getItemName());
//        menuGrid.setWidget(newRow, 1, foodItemLabel);
//
//        TextBox priceTextBox = new TextBox();
//        priceTextBox.setText(Double.toString(newItem.getPrice()));
//        menuGrid.setWidget(newRow, 2, priceTextBox);
//	}
//}
