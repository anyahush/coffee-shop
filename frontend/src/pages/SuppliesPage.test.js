// src/pages/SuppliesPage.test.jsx
/* eslint-env jest */

import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import SuppliesPage from './SuppliesPage';
import * as api from '../api/supplyApi';

jest.mock('../api/supplyApi');

describe('SuppliesPage', () => {
    const mockItems = [{ id:1,name:'X',category:'Y',quantityInStock:5,reorderLevel:2,supplier:{name:'Z'} }];

    beforeEach(() => {
        api.getAllSupplies.mockResolvedValue({ data: mockItems });
        api.searchSupplies.mockResolvedValue({ data: mockItems });
    });

    test('loads and displays items on mount', async () => {
        render(<SuppliesPage />);
        // wait for the table row to appear
        expect(await screen.findByText('X')).toBeInTheDocument();
    });

    test('search calls searchSupplies and updates list', async () => {
        render(<SuppliesPage />);
        // wait for initial load
        await screen.findByText('X');

        // simulate search
        fireEvent.change(screen.getByRole('combobox'), { target: { value: 'name' } });
        fireEvent.change(screen.getByPlaceholderText('Search…'), { target: { value: 'X' } });
        fireEvent.click(screen.getByRole('button', { name: 'Search' }));

        await waitFor(() => {
            expect(api.searchSupplies).toHaveBeenCalledWith({ name: 'X' });
            // and we still see our item
            expect(screen.getByText('X')).toBeInTheDocument();
        });
    });

    test('delete calls deleteSupply and reloads', async () => {
        api.deleteSupply = jest.fn().mockResolvedValue({});
        render(<SuppliesPage />);
        // initial load
        await screen.findByText('X');

        fireEvent.click(screen.getByText('Delete'));
        await waitFor(() => {
            expect(api.deleteSupply).toHaveBeenCalledWith(1);
            expect(api.getAllSupplies).toHaveBeenCalledTimes(2);
        });
    });
});
