// src/components/SearchBar.jsx
import React, { useState } from 'react';

export default function SearchBar({ onSearch }) {
    // 1) Which field to filter by?
    const [field, setField] = useState('name');
    // 2) What text to filter on?
    const [term, setTerm]   = useState('');

    // 3) Called on Search (or Enter)
    const handleSubmit = e => {
        e.preventDefault();
        const text = term.trim();
        // empty → clear filters
        if (!text) {
            return onSearch({});
        }
        // dynamic key: { name: "foo" } or { category: "bar" }
        onSearch({ [field]: text });
    };

    // 4) Called on Reset
    const handleReset = () => {
        setField('name');
        setTerm('');
        onSearch({});
    };

    return (
        <form onSubmit={handleSubmit} className="row g-2 align-items-center mb-4">
            {/* Field selector */}
            <div className="col-auto">
                <select
                    className="form-select"
                    value={field}
                    onChange={e => setField(e.target.value)}
                >
                    <option value="name">Name</option>
                    <option value="category">Category</option>
                    <option value="supplier">Supplier</option>
                </select>
            </div>

            {/* Text input */}
            <div className="col-auto flex-grow-1">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Search…"
                    value={term}
                    onChange={e => setTerm(e.target.value)}
                />
            </div>

            {/* Search button */}
            <div className="col-auto">
                <button type="submit" className="btn btn-primary">
                    Search
                </button>
            </div>

            {/* Reset button */}
            <div className="col-auto">
                <button
                    type="button"
                    className="btn btn-outline-secondary"
                    onClick={handleReset}
                >
                    Reset
                </button>
            </div>
        </form>
    );
}
