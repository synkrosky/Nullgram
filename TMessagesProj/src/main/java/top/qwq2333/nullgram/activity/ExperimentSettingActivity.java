package top.qwq2333.nullgram.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.NotificationsCheckCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.RecyclerListView;

import top.qwq2333.nullgram.config.ConfigManager;
import top.qwq2333.nullgram.utils.Defines;

@SuppressLint("NotifyDataSetChanged")
public class ExperimentSettingActivity extends BaseActivity {


    private int experimentRow;
    private int blockSponsorAdsRow;
    private int syntaxHighlightRow;
    private int aliasChannelRow;
    private int keepFormattingRow;
    private int enchantAudioRow;
    private int linkedUserRow;
    private int overrideChannelAliasRow;
    private int experiment2Row;


    @Override
    protected void onItemClick(View view, int position, float x, float y) {
        if (position == blockSponsorAdsRow) {
            ConfigManager.toggleBoolean(Defines.blockSponsorAds);
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(ConfigManager.getBooleanOrFalse(Defines.blockSponsorAds));
            }
        } else if (position == syntaxHighlightRow) {
            ConfigManager.putBoolean(Defines.codeSyntaxHighlight, ConfigManager.getBooleanOrDefault(Defines.codeSyntaxHighlight, true));
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(ConfigManager.getBooleanOrDefault(Defines.codeSyntaxHighlight, true));
            }
        } else if (position == aliasChannelRow) {
            boolean currentStatus = ConfigManager.getBooleanOrFalse(Defines.channelAlias);
            if (!currentStatus && !ConfigManager.getBooleanOrFalse(Defines.labelChannelUser)) {
                ConfigManager.putBoolean(Defines.labelChannelUser, true);
            }
            ConfigManager.toggleBoolean(Defines.channelAlias);
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(ConfigManager.getBooleanOrFalse(Defines.channelAlias));
            }
        } else if (position == keepFormattingRow) {
            ConfigManager.toggleBoolean(Defines.keepCopyFormatting);
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(ConfigManager.getBooleanOrFalse(Defines.keepCopyFormatting));
            }
        } else if (position == enchantAudioRow) {
            ConfigManager.toggleBoolean(Defines.enchantAudio);
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(ConfigManager.getBooleanOrFalse(Defines.enchantAudio));
            }
        } else if (position == linkedUserRow) {
            ConfigManager.toggleBoolean(Defines.linkedUser);
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(ConfigManager.getBooleanOrFalse(Defines.linkedUser));
            }
        } else if (position == overrideChannelAliasRow) {
            ConfigManager.toggleBoolean(Defines.overrideChannelAlias);
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(ConfigManager.getBooleanOrFalse(Defines.overrideChannelAlias));
            }
        }

    }

    @Override
    protected boolean onItemLongClick(View view, int position, float x, float y) {
        // ignore
        return false;
    }

    @Override
    protected String getKey() {
        return "e";
    }

    @Override
    protected void updateRows() {
        super.updateRows();

        experimentRow = rowCount++;
        if (ConfigManager.getBooleanOrFalse(Defines.showHiddenSettings)) {
            blockSponsorAdsRow = rowCount++;
        }
        syntaxHighlightRow = rowCount++;
        aliasChannelRow = rowCount++;
        keepFormattingRow = rowCount++;
        enchantAudioRow = rowCount++;
        linkedUserRow = rowCount++;
        if (ConfigManager.getBooleanOrFalse(Defines.linkedUser) && ConfigManager.getBooleanOrFalse(Defines.labelChannelUser)) {
            overrideChannelAliasRow = rowCount++;
        } else {
            overrideChannelAliasRow = -1;
        }
        experiment2Row = rowCount++;

        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new ListAdapter(context);
    }

    @Override
    protected String getActionBarTitle() {
        return LocaleController.getString("Experiment", R.string.Experiment);
    }

    private class ListAdapter extends BaseListAdapter {

        public ListAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 1: {
                    if (position == experiment2Row) {
                        holder.itemView.setBackground(Theme.getThemedDrawable(mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    } else {
                        holder.itemView.setBackground(Theme.getThemedDrawable(mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    }
                    break;
                }
                case 2: {
                    TextSettingsCell textCell = (TextSettingsCell) holder.itemView;
                    textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    break;
                }
                case 3: {
                    TextCheckCell textCell = (TextCheckCell) holder.itemView;
                    textCell.setEnabled(true, null);
                    if (position == blockSponsorAdsRow) {
                        textCell.setTextAndCheck(LocaleController.getString("blockSponsorAds", R.string.blockSponsorAds), ConfigManager.getBooleanOrFalse(Defines.blockSponsorAds), true);
                    } else if (position == syntaxHighlightRow) {
                        textCell.setTextAndValueAndCheck(LocaleController.getString("codeSyntaxHighlight", R.string.codeSyntaxHighlight), LocaleController.getString("codeSyntaxHighlightDetails", R.string.codeSyntaxHighlightDetails), ConfigManager.getBooleanOrFalse(Defines.codeSyntaxHighlight), true, true);
                    } else if (position == aliasChannelRow) {
                        textCell.setTextAndValueAndCheck(LocaleController.getString("channelAlias", R.string.channelAlias), LocaleController.getString("channelAliasDetails", R.string.channelAliasDetails), ConfigManager.getBooleanOrFalse(Defines.channelAlias), true, true);
                    } else if (position == keepFormattingRow) {
                        textCell.setTextAndCheck(LocaleController.getString("keepFormatting", R.string.keepFormatting), ConfigManager.getBooleanOrFalse(Defines.keepCopyFormatting), true);
                    } else if (position == enchantAudioRow) {
                        textCell.setTextAndValueAndCheck(LocaleController.getString("enchantAudioRow", R.string.enchantAudio), LocaleController.getString("enchantAudioDetails", R.string.enchantAudioDetails), ConfigManager.getBooleanOrFalse(Defines.enchantAudio), true, true);
                    } else if (position == linkedUserRow) {
                        textCell.setTextAndValueAndCheck(LocaleController.getString("linkUser", R.string.linkUser), LocaleController.getString("linkUserDetails", R.string.linkUserDetails), ConfigManager.getBooleanOrFalse(Defines.linkedUser), true, true);
                    } else if (position == overrideChannelAliasRow) {
                        textCell.setTextAndValueAndCheck(
                            ConfigManager.getBooleanOrFalse(Defines.channelAlias) ?
                                LocaleController.getString("overrideChannelAlias", R.string.overrideChannelAlias) :
                                LocaleController.getString("labelChannelInChat", R.string.labelChannelInChat),
                            LocaleController.getString("overrideChannelAliasDetails", R.string.overrideChannelAliasDetails),
                            ConfigManager.getBooleanOrFalse(Defines.overrideChannelAlias), true, true);
                    }
                    break;
                }
                case 4: {
                    HeaderCell headerCell = (HeaderCell) holder.itemView;
                    if (position == experimentRow) {
                        headerCell.setText(LocaleController.getString("Experiment", R.string.Experiment));
                    }
                    break;
                }
                case 5: {
                    NotificationsCheckCell textCell = (NotificationsCheckCell) holder.itemView;
                    break;
                }
            }
        }

        @Override
        public boolean isEnabled(RecyclerView.ViewHolder holder) {
            int type = holder.getItemViewType();
            return type == 2 || type == 3 || type == 6 || type == 5;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 1:
                    view = new ShadowSectionCell(mContext);
                    break;
                case 2:
                    view = new TextSettingsCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    view = new TextCheckCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 4:
                    view = new HeaderCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 5:
                    view = new NotificationsCheckCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 6:
                    view = new TextDetailSettingsCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 7:
                    view = new TextInfoPrivacyCell(mContext);
                    view.setBackground(Theme.getThemedDrawable(mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            //noinspection ConstantConditions
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new RecyclerListView.Holder(view);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == experiment2Row) {
                return 1;
            } else if (position == experimentRow) {
                return 4;
            }
            return 3;
        }
    }
}
