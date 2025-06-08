/* eslint-env jest */
import { render, screen, fireEvent } from '@testing-library/react';
import SearchBar from './SearchBar';

describe('SearchBar', () => {
    const mockSearch = jest.fn();

    beforeEach(() => {
        mockSearch.mockClear();
    });

    test('renders dropdown, input, and buttons', () => {
        render(<SearchBar onSearch={mockSearch} />);

        expect(screen.getByRole('combobox')).toHaveValue('name');
        expect(screen.getByPlaceholderText('Search…')).toHaveValue('');
        expect(screen.getByRole('button', { name: 'Search' })).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Reset' })).toBeInTheDocument();
    });

    test('calls onSearch with correct param when searching', () => {
        render(<SearchBar onSearch={mockSearch} />);

        // change field to "category"
        fireEvent.change(screen.getByRole('combobox'), { target: { value: 'category' } });
        fireEvent.change(screen.getByPlaceholderText('Search…'), { target: { value: 'tea' } });

        fireEvent.click(screen.getByRole('button', { name: 'Search' }));
        expect(mockSearch).toHaveBeenCalledWith({ category: 'tea' });
    });

    test('reset clears inputs and calls onSearch({})', () => {
        render(<SearchBar onSearch={mockSearch} />);

        // pre‐populate
        fireEvent.change(screen.getByRole('combobox'), { target: { value: 'supplier' } });
        fireEvent.change(screen.getByPlaceholderText('Search…'), { target: { value: 'Acme' } });

        fireEvent.click(screen.getByRole('button', { name: 'Reset' }));
        expect(screen.getByRole('combobox')).toHaveValue('name');
        expect(screen.getByPlaceholderText('Search…')).toHaveValue('');
        expect(mockSearch).toHaveBeenCalledWith({});
    });
});
