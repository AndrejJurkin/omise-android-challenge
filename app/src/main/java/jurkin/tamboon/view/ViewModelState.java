package jurkin.tamboon.view;

/**
 * The ViewModelState represents the states that we can reuse and subscribe to in view models.
 *
 * Created by Andrej Jurkin on 12/28/17.
 */

public enum ViewModelState {

    /**
     * The view model has been loaded successfully.
     * At this point the view should display the data and hide the loading indicator.
     */
    Loaded,

    /**
     * The view model data is loading.
     * At this point the view should show a loading indicator.
     */
    Loading,

    /**
     * The view model data has been loaded, but the data set is empty.
     * At this point the view should display an empty state view.
     */
    Empty,

    /**
     * The view model has failed to load data.
     * At this point the view should display an error state view.
     */
    Error
}
