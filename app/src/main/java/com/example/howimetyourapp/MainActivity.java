package com.example.howimetyourapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CharacterAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    private List<Character> characterList;
    private List<Character> filteredCharacterList;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        characterList = new ArrayList<>();
        populateCharacterList();

        filteredCharacterList = new ArrayList<>(characterList);

        adapter = new CharacterAdapter(this, filteredCharacterList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        setupSearch();
    }

    private void populateCharacterList() {
        characterList.add(new Character("Ted Mosby", "Architect and the main protagonist who narrates the story of how he met the mother of his children", R.drawable.ted_mosby));
        characterList.add(new Character("Barney Stinson", "Womanizer with a mysterious job who is known for his catchphrases, suits, and elaborate playbooks", R.drawable.barney_stinson));
        characterList.add(new Character("Robin Scherbatsky", "Canadian news reporter with a passion for guns, scotch, and cigars, who struggles with emotional commitment", R.drawable.robin_scherbatsky));
        characterList.add(new Character("Marshall Eriksen", "Environmental lawyer from Minnesota who is Ted's college roommate and Lily's husband", R.drawable.marshall_eriksen));
        characterList.add(new Character("Lily Aldrin", "Kindergarten teacher and artist who is Marshall's wife and has been with him since college", R.drawable.lily_aldrin));
        characterList.add(new Character("Tracy McConnell", "The titular 'Mother' who plays bass guitar and eventually marries Ted", R.drawable.tracy_mcconnell));
        characterList.add(new Character("Ranjit Singh", "Cab driver and later a limo driver who frequently drives the gang around", R.drawable.ranjit_singh));
        characterList.add(new Character("Carl MacLaren", "Bartender at MacLaren's Pub where the gang hangs out", R.drawable.carl_maclaren));
        characterList.add(new Character("James Stinson", "Barney's gay half-brother who shares many of Barney's traits", R.drawable.james_stinson));
        characterList.add(new Character("Victoria", "Ted's ex-girlfriend who is a pastry chef and was once living in Germany", R.drawable.victoria));
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCharacters(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterCharacters(String searchText) {
        filteredCharacterList.clear();

        if (searchText.isEmpty()) {
            filteredCharacterList.addAll(characterList);
        } else {
            String searchLower = searchText.toLowerCase();
            for (Character character : characterList) {
                if (character.getName().toLowerCase().contains(searchLower) ||
                        character.getDescription().toLowerCase().contains(searchLower)) {
                    filteredCharacterList.add(character);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Character character = filteredCharacterList.get(position);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_character_info, null);

        ImageView characterImage = dialogView.findViewById(R.id.characterImage);
        TextView characterName = dialogView.findViewById(R.id.characterName);
        TextView characterDescription = dialogView.findViewById(R.id.characterDescription);

        characterImage.setImageResource(character.getImageResourceId());
        characterName.setText(character.getName());
        characterDescription.setText(character.getDescription());

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Close", null)
                .show();
    }
}