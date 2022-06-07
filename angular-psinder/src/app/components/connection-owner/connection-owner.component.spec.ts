import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectionOwnerComponent } from './connection-owner.component';

describe('ConnectionOwnerComponent', () => {
  let component: ConnectionOwnerComponent;
  let fixture: ComponentFixture<ConnectionOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectionOwnerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConnectionOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
