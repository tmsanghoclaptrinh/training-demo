package com.cuonglamchi;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cuonglamchi.model.FunctionItem;
import com.cuonglamchi.model.ShortcutItem;
import com.cuonglamchi.model.User;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.menu_activity);
        setupWindowInsets();

        // Create a demo user
        User user = createDemoUser();

        // Setup UI elements
        setupUser(user);
        setupShortcutItems(user.getShortcuts());
        setupGridFunctionItems();
        setupBottomNavigationItems();
    }

    /**
     * Adjust the padding of a view based on system insets (like status bar, navigation bar, ...)
     */
    private void setupWindowInsets() {
        // Set a listener to handle window insets (such as status bar, navigation bar, ...)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Get the insets for the system bars (status bar, navigation bar, ...)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Adjust the padding of the view to accommodate system bar insets
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Return the insets to let the system handle them further if needed
            return insets;
        });
    }

    /**
     * Create a demo user
     *
     * @return An user
     */
    private User createDemoUser() {
        List<ShortcutItem> shortcuts = new ArrayList<>();
        shortcuts.add(new ShortcutItem(R.drawable.genzlamit, true, getString(R.string.group_1)));
        shortcuts.add(new ShortcutItem(R.drawable.nguyenat, false, getString(R.string.username_1)));
        shortcuts.add(new ShortcutItem(R.drawable.ducbui, false, getString(R.string.username_2)));
        shortcuts.add(new ShortcutItem(R.drawable.truongmanhthang, false, getString(R.string.username_3)));
        shortcuts.add(new ShortcutItem(R.drawable.xoaixanh, false, getString(R.string.username_4)));
        return new User("Cuong Lam Chi", R.drawable.baseline_account_circle_40, shortcuts);
    }

    /**
     * Set up the avatar and full name
     *
     * @param user An user
     */
    private void setupUser(User user) {
        ImageView avatarImageView = findViewById(R.id.avatar);
        avatarImageView.setImageResource(user.getAvatarSourceId());

        TextView fullNameTextView = findViewById(R.id.fullName);
        fullNameTextView.setText(user.getFullName());
    }

    /**
     * Set up the list of shortcut items
     */
    private void setupShortcutItems(List<ShortcutItem> shortcuts) {
        LinearLayout shortcutList = findViewById(R.id.shortcutList);

        for (ShortcutItem shortcutItem : shortcuts) {
            addShortcutItemToList(shortcutList, shortcutItem);
        }
    }

    /**
     * Add a shortcut item to the shortcut list
     *
     * @param shortcutList ShortcutList layout
     * @param shortcutItem Shortcut item
     */

    // Add a shortcut item to the shortcut list
    private void addShortcutItemToList(LinearLayout shortcutList, ShortcutItem shortcutItem) {
        // Create a linear layout for each shortcut item
        LinearLayout itemLayout = createShortcutLayout();

        // Create and add avatar view
        RelativeLayout relativeLayout = createRelativeLayout();
        ImageView avatarImageView = createAvatarImageView(shortcutItem.getAvatar());
        LinearLayout cardViewWrapper = createCardViewWrapperWithAvatar(avatarImageView, shortcutItem.isGroup());
        relativeLayout.addView(cardViewWrapper);

        // Create and add appropriate icon for group or individual shortcuts
        if (shortcutItem.isGroup()) {
            createGroupIcon(relativeLayout);
        } else {
            createPersonalIcon(relativeLayout);
        }
        itemLayout.addView(relativeLayout);

        // Add shortcut name
        TextView textView = createShortcutTextView(shortcutItem.getName());
        itemLayout.addView(textView);

        // Add item to the shortcut list
        shortcutList.addView(itemLayout);
    }

    /**
     * Convert dp to px
     *
     * @param dp dp value
     * @return pixel value
     */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * Create a shortcut layout
     *
     * @return A shortcut layout
     */
    private LinearLayout createShortcutLayout() {
        LinearLayout itemLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                dpToPx(80),
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.setMarginEnd(dpToPx(16));
        itemLayout.setLayoutParams(linearLayoutParams);
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        return itemLayout;
    }

    /**
     * Create a relative layout for shortcut avatars and icons
     *
     * @return A RelativeLayout
     */
    private RelativeLayout createRelativeLayout() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                dpToPx(80),
                dpToPx(80)
        ));
        relativeLayout.setPadding(dpToPx(0), dpToPx(4), dpToPx(0), dpToPx(0));
        return relativeLayout;
    }

    /**
     * Create an avatar image view for shortcuts
     *
     * @param shortcutAvatarResourceId Resource id of the shortcut avatar
     * @return An ImageView
     */
    private ImageView createAvatarImageView(int shortcutAvatarResourceId) {
        ImageView avatarImageView = new ImageView(this);
        avatarImageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        avatarImageView.setBackgroundResource(R.drawable.baseline_circle_white_24);
        avatarImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        avatarImageView.setImageResource(shortcutAvatarResourceId);

        return avatarImageView;
    }

    /**
     * Create a card view wrapper with an avatar image
     *
     * @param avatarImageView ImageView of the avatar
     * @param isGroup Is this a group or a personal
     * @return A LinearLayout of the card view wrapper
     */
    private LinearLayout createCardViewWrapperWithAvatar(ImageView avatarImageView, boolean isGroup) {
        LinearLayout cardViewWrapper = new LinearLayout(this);
        cardViewWrapper.setLayoutParams(new LinearLayout.LayoutParams(
                dpToPx(70),
                dpToPx(70)
        ));

        CardView cardView = new CardView(this);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        if (isGroup) {
            cardView.setRadius(dpToPx(10));
        }
        else {
            cardView.setRadius(dpToPx(35));
        }

        cardView.addView(avatarImageView);

        cardViewWrapper.addView(cardView);
        return cardViewWrapper;
    }

    /**
     * Add group icon to the shortcut
     *
     * @param relativeLayout Parent of the group icon
     */
    private void createGroupIcon(RelativeLayout relativeLayout) {
        LinearLayout iconWrapper = new LinearLayout(this);
        RelativeLayout.LayoutParams relativeLayoutParams =
                new RelativeLayout.LayoutParams(
                        dpToPx(30),
                        dpToPx(30)
                );
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        iconWrapper.setLayoutParams(relativeLayoutParams);
        iconWrapper.setBackgroundResource(R.drawable.baseline_circle_white_24);
        iconWrapper.setPadding(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));

        ImageView iconImageView = new ImageView(this);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        imageViewParams.gravity = Gravity.CENTER;
        iconImageView.setLayoutParams(imageViewParams);
        iconImageView.setBackgroundResource(R.drawable.baseline_circle_blue_24);
        iconImageView.setImageResource(R.drawable.baseline_groups_24);
        iconImageView.setPadding(dpToPx(3), dpToPx(3), dpToPx(3), dpToPx(3));

        iconWrapper.addView(iconImageView);

        relativeLayout.addView(iconWrapper);
    }

    /**
     * Add personal icon to the shortcut
     *
     * @param relativeLayout Parent of the personal icon
     */
    private void createPersonalIcon(RelativeLayout relativeLayout) {
        ImageView iconImageView = new ImageView(this);
        RelativeLayout.LayoutParams relativeLayoutParams =
                new RelativeLayout.LayoutParams(
                        dpToPx(30),
                        dpToPx(30)
                );
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        iconImageView.setLayoutParams(relativeLayoutParams);
        iconImageView.setBackgroundResource(R.drawable.baseline_circle_white_24);
        iconImageView.setImageResource(R.drawable.baseline_people_alt_24);
        iconImageView.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));

        relativeLayout.addView(iconImageView);
    }

    /**
     * Create a text view for shortcut name
     *
     * @param shortcutName String of the shortcut name
     * @return A TextView
     */
    private TextView createShortcutTextView(String shortcutName) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textView.setText(shortcutName);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return textView;
    }

    /**
     * Set up function items in the grid layout
     */
    private void setupGridFunctionItems() {
        GridLayout gridFunctions = findViewById(R.id.gridFunctions);

        List<FunctionItem> functions = getFunctionList();
        for (FunctionItem function : functions) {
            addFunctionItemToGrid(gridFunctions, function);
        }
    }

    /**
     * Generate a list of function items
     *
     * @return A list of function items
     */
    private List<FunctionItem> getFunctionList() {
        List<FunctionItem> functions = new ArrayList<>();
        functions.add(new FunctionItem(R.drawable.baseline_bookmark_24, getString(R.string.function_saved)));
        functions.add(new FunctionItem(R.drawable.baseline_groups_24, getString(R.string.function_groups)));
        functions.add(new FunctionItem(R.drawable.outline_watch_later_24, getString(R.string.function_memories)));
        functions.add(new FunctionItem(R.drawable.baseline_ondemand_video_24, getString(R.string.function_video)));
        functions.add(new FunctionItem(R.drawable.baseline_storefront_24, getString(R.string.function_marketplace)));
        functions.add(new FunctionItem(R.drawable.baseline_people_alt_24, getString(R.string.function_friends)));
        functions.add(new FunctionItem(R.drawable.baseline_feed_24, getString(R.string.function_feeds)));
        functions.add(new FunctionItem(R.drawable.baseline_heart_broken_24, getString(R.string.function_dating)));

        return functions;
    }

    /**
     * Add a function item to the grid
     *
     * @param gridFunctions GridLayout of the gridFunctions
     * @param function      A function item
     */
    private void addFunctionItemToGrid(GridLayout gridFunctions, FunctionItem function) {
        LinearLayout itemLayout = createFunctionLayout();

        ImageView imageView = createFunctionIcon(function.getIcon());
        itemLayout.addView(imageView);

        TextView textView = createFunctionTextView(function.getName());
        itemLayout.addView(textView);

        gridFunctions.addView(itemLayout);
    }

    /**
     * Create a function layout
     *
     * @return A LinearLayout
     */
    private LinearLayout createFunctionLayout() {
        LinearLayout itemLayout = new LinearLayout(this);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));
        layoutParams.width = dpToPx(0);
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        itemLayout.setLayoutParams(layoutParams);
        itemLayout.setBackgroundResource(R.drawable.rectangle_background);
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
        return itemLayout;
    }

    /**
     * Create a text view for a function name
     *
     * @param functionName Name of the function
     * @return A TextView
     */
    private TextView createFunctionTextView(String functionName) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.setMargins(0, dpToPx(8), 0, 0);
        textView.setLayoutParams(linearLayoutParams);
        textView.setText(functionName);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return textView;
    }

    /**
     * Create a icon for a function item
     *
     * @param functionIconResourceId Resource id of the function icon
     * @return An ImageView
     */
    private ImageView createFunctionIcon(int functionIconResourceId) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                dpToPx(40),
                dpToPx(40)
        ));
        if (functionIconResourceId == R.drawable.baseline_groups_24) {
            imageView.setBackgroundResource(R.drawable.baseline_circle_blue_24);
            imageView.setPadding(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
        }
        imageView.setImageResource(functionIconResourceId);
        return imageView;
    }

    /**
     * Set up bottom navigation items
     */
    private void setupBottomNavigationItems() {
        LinearLayout bottomNavigation = findViewById(R.id.bottomNavigation);
        addBottomNavigationItem(bottomNavigation, R.drawable.baseline_home_24,
                getString(R.string.navigation_home), 0);
        addBottomNavigationItem(bottomNavigation, R.drawable.baseline_ondemand_video_24,
                getString(R.string.navigation_video), 0);
        addBottomNavigationItem(bottomNavigation, R.drawable.baseline_storefront_24,
                getString(R.string.navigation_marketplace), 0);
        addBottomNavigationItem(bottomNavigation, R.drawable.baseline_notifications_none_24,
                getString(R.string.navigation_notifications), 0);
        addBottomNavigationItem(bottomNavigation, R.drawable.baseline_menu_24_blue,
                getString(R.string.navigation_menu), R.color.blue);
    }

    /**
     * Add bottom navigation item to the layout
     *
     * @param parentLayout        The LinearLayout with id=bottomNavigation
     * @param iconResourceId      Resource id of the icon
     * @param label               Label of the item
     * @param textColorResourceId Resource id of the text color
     */
    private void addBottomNavigationItem(LinearLayout parentLayout, int iconResourceId, String label, int textColorResourceId) {
        // Create a LinearLayout (vertical stack) to wrap the icon and the text view
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setGravity(Gravity.CENTER);

        // Create an ImageView for the icon
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(24), dpToPx(24)));
        imageView.setImageResource(iconResourceId);

        // Create a TextView for the label
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(label);
        textView.setTextSize(10);

        // If the menu is selected, it will be change the text color to blue
        if (textColorResourceId != 0) {
            textView.setTextColor(ContextCompat.getColor(this, textColorResourceId));
        }

        // Add ImageView and TextView into the itemLayout
        itemLayout.addView(imageView);
        itemLayout.addView(textView);

        // Add itemLayout into the parent layout
        parentLayout.addView(itemLayout);
    }

}
